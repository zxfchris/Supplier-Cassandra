package edu.nus.cs4224;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import edu.nus.cs4224.d8.*;

public class Supplier {
	
	public static Session session ;
	private static MappingManager manager;
	private static MyAccessor myAccessor;
	private Mapper<District> d_mapper;
	private Mapper<Customer> c_mapper;
	private Mapper<Item> i_mapper;
	private Mapper<Order> o_mapper;
	private Mapper<Warehouse> w_mapper;
	private Mapper<OrderLine> ol_mapper;
	
	public Supplier() {
		CassandraConnector connector = new CassandraConnector();
		connector.connect(Constants.NODE, Constants.PORT, Constants.KEYSPACE);
		session = connector.getSession();
		manager = new MappingManager(session);
		myAccessor = manager.createAccessor(MyAccessor.class);
		d_mapper = manager.mapper(District.class);
		c_mapper = manager.mapper(Customer.class);
		i_mapper = manager.mapper(Item.class);
		o_mapper = manager.mapper(Order.class);
		ol_mapper = manager.mapper(OrderLine.class);
	}

	public static void main(String[] args) {
		Supplier supplier = new Supplier();
		//supplier.query("warehouse");
		//BigDecimal ytd = new BigDecimal(20);
		//supplier.paymentTran(1, 5, 10, ytd);
		//TableCreator creator = new TableCreator();
		//creator.run();
		supplier.queryDeliveryTran(1, 10);
	}
	
	public void paymentTran(int w_id, int d_id, int c_id, BigDecimal paymentAmount) {
		//update warehouse
		String warehouseQuery = "select * from d8.warehouse where w_id =" + w_id;
		ResultSet results = session.execute(warehouseQuery);
		BigDecimal originYTD = results.all().get(0).getDecimal("w_ytd");
		String updateWarehouse = "update d8.warehouse set w_ytd = " 
				+ originYTD.add(paymentAmount)
				+ " where w_id = " + w_id;
		session.execute(updateWarehouse);
		
		//update district
		String districtQuery = "select * from d8.district where d_w_id =" + w_id
				+" and d_id ="+d_id;
		results = session.execute(districtQuery);
		originYTD = results.all().get(0).getDecimal("d_ytd");
		String updateDistrict = "update d8.district set d_ytd = " 
				+ originYTD.add(paymentAmount)
				+ " where d_w_id = " + w_id + " and d_id="+d_id;
		session.execute(updateDistrict);
		
		//update customer
		String customerQuery = "select * from d8.customer where c_w_id =" + w_id
				+" and c_d_id ="+d_id + " and c_id="+c_id;
		results = session.execute(customerQuery);
		Row row = results.all().get(0);
		BigDecimal originBlc = row.getDecimal("c_balance");
		float originYTDPmt = row.getFloat("c_ytd_payment");
		int pmtCnt = row.getInt("c_payment_cnt");
		String updateCustomer = "update d8.customer set c_balance = " 
				+ originBlc.subtract(paymentAmount)
				+ " ,c_ytd_payment=" + (originYTDPmt + paymentAmount.floatValue())
				+ " ,c_payment_cnt=" + (pmtCnt + 1)
				+ " where c_w_id = " + w_id + " and c_d_id="+d_id+ " and c_id="+c_id;
		session.execute(updateCustomer);
	}
	
	public void insert(String table, String[] names, Object[] values) {
		session.execute(QueryBuilder.insertInto(table).values(names, values));
	}
	
	public void delete(String table, String key, String value) {
		session.execute(QueryBuilder.delete()
				   .from(table)
				   .where(QueryBuilder.eq(key, value)));
	}
	
	public void query() {
		//session.execute(QueryBuilder.select().from("d8.warehouse").where().equals(1));
		Statement statement = QueryBuilder.select()
		        .all()
		        .from("d8", "warehouse");
		        //.where(eq("",""));
		ResultSet results = session.execute(statement);
		for ( Row row : results ) {
		    System.out.println("warehouse: " + row.getString("w_name"));
		}
	}
	
	public void query(String table) {
		ResultSet results = session.execute("select * from d8.warehouse;"); //TODO
		for (Row row : results) {
			System.out.println("w_id:" + row.getInt("w_id") + " w_name:" + row.getString("w_name"));
		}
		Mapper<Warehouse> mapper = manager.mapper(Warehouse.class);
		Warehouse wh = new Warehouse( 9, "exdfaefasdfas", "", "", "", 
				"", "", null, null);
		//mapper.save(wh);
		Warehouse w1 = mapper.get(1);
		System.out.println(w1.getW_name());
		
		Mapper<District> distrMapper = manager.mapper(District.class);
		District d1 = distrMapper.get(1, 5);
		System.out.println(d1.getD_name());
		
		Mapper<Order> orderMapper = manager.mapper(Order.class);
		Order o1 = orderMapper.get("5,1,3");
		System.out.println(o1.getO_carrier_id());
	}
	
	/**
	 * Stock-level Transaction (transaction 5)
	 * @param w_id	Warehouse number W_ID
	 * @param d_id	District number D_ID
	 * @param l 	Number of last sold items to be examined L
	 * @param t		Stock threshold T
	 * @return
	 */
	public List<Integer> queryStocks(int w_id, int d_id, int l, int t) {
		District district = d_mapper.get(w_id, d_id);
		int d_next_o_id = district.getD_next_o_id();
		
		Result<OrderLine> ol_List = myAccessor.getLastLOrdersLine(w_id, d_id, d_next_o_id - l, d_next_o_id);
		List<Integer> stockAlert = new ArrayList<Integer>();
		for (OrderLine ol : ol_List) {
			if (ol.getOl_i_stock().doubleValue() < t) {
				stockAlert.add(ol.getOl_i_id());
			}
		}
		return stockAlert;
	}
	/**
	 * Delivery Transaction (transaction 3)
	 * @param w_id	Warehouse number W_ID
	 * @param carrier_id	CARRIER_ID
	 * @return
	 */
	public void queryDeliveryTran(int w_id, int carrier_id) {
		Result<District> districts = myAccessor.getDistrictByWid(w_id);
		for (District distr : districts) {
			int d_id = distr.getD_id();
			System.out.println("district:" + d_id);
			Result<Order> orders = myAccessor.getOrderByDistrict(w_id, d_id);
			Order X = orders.one();
			int c_id = X.getO_c_id();
			int N = X.getO_id();
			X.setO_carrier_id(carrier_id);
			o_mapper.save(X);
			Result<OrderLine> ol_List = myAccessor.getOrderLinesByOrder(w_id, d_id, N);
			Date dt = new Date();
			BigDecimal B = new BigDecimal(0);	//B: the sum of OL_AMOUNT for all the items placed in order X
			for (OrderLine ol : ol_List) {
				ol.setOl_delivery_d(dt);
				ol_mapper.save(ol);
				B.add(ol.getOl_amount());
			}
			Customer customer = c_mapper.get(w_id, d_id, c_id);
			customer.setC_balance(customer.getC_balance().add(B));
			customer.setC_delivery_cnt(customer.getC_delivery_cnt() + 1);
			c_mapper.save(customer);
		}
	}
	/**
	 * Delivery Transaction (transaction 4)
	 * @param w_id	Warehouse number W_ID
	 * @param carrier_id	CARRIER_ID
	 * @return
	 */
	public void queryOrderStatus(int c_w_id, int c_d_id, int c_id) {
		Customer customer = c_mapper.get(c_w_id, c_d_id, c_id);
		System.err.println(customer.getC_first() + "," + customer.getC_middle() + "," + customer.getC_last()
				+ " : " + customer.getC_balance().toString());
		Result<Order> orders = myAccessor.getOrderByCustomer(c_w_id, c_d_id, c_id);
		Order lastOrder = orders.one();
		System.err.println("O_ID:"+lastOrder.getO_id()+" O_ENTRY_D:"+lastOrder.getO_entry_d().toString()
				+ " O_CARRIER_ID:"+lastOrder.getO_carrier_id());
		Result<OrderLine> ol_list = myAccessor.getOrderLinesByOrder(c_w_id, c_d_id, lastOrder.getO_id());
		for (OrderLine ol : ol_list) {
			System.err.println("OL_I_ID:" + ol.getOl_i_id() + " OL_SUPPLY_W_ID:" + ol.getOl_supply_w_id()
					+ " OL_QUANTITY:" + ol.getOl_quantity() + " OL_AMOUNT" + ol.getOl_amount()
					+ " OL_DELIVERY_D:" + ol.getOl_delivery_d());
		}
	}
	/**
	 * Popular-Item Transaction (transaction 6)
	 * @param w_id	Warehouse number W_ID
	 * @param d_id	District number D_ID
	 * @param l		Number of last orders to be examined L
	 */
	public void queryPopularItems(int w_id, int d_id, int l) {
		Map<Item, BigDecimal> popularItems = new HashMap<Item, BigDecimal>();
		Map<Item, Integer> orderNums = new HashMap<Item, Integer>();
		
		District district = d_mapper.get(w_id, d_id);
		int d_next_o_id = district.getD_next_o_id();
		
		Result<Order> last_L_Orders = myAccessor.getLastLOrders(w_id, d_id, d_next_o_id-l, d_next_o_id);
		for (Order o : last_L_Orders) {
			int order_id = o.getO_id();
			Date entry_date = o.getO_entry_d();
			int c_id = o.getO_c_id();
			Customer c = c_mapper.get(w_id, d_id, c_id);
			String first_name = c.getC_first();
			String middle_name = c.getC_middle();
			String last_name = c.getC_last();
			System.out.println(order_id + "_" + entry_date + ":" + first_name + "," + middle_name + "," + last_name);
		
			Result<OrderLine> o_ols = myAccessor.getOrderLinesByOrder(w_id, d_id, o.getO_id());
			List<OrderLine> ol_List = new ArrayList<OrderLine>();
			for (OrderLine ol : o_ols) {
				ol_List.add(ol);
			}
			OLComparator olC = new OLComparator();
			Collections.sort(ol_List, olC);			//sort order_lines with an descending order
			
			OrderLine ol = ol_List.iterator().next();
			BigDecimal max = ol.getOl_quantity();
			Item item = i_mapper.get(ol.getOl_i_id());
			updatePopularItems(popularItems, ol, item, orderNums);
			while ((ol = ol_List.iterator().next()) != null) {
				if (ol.getOl_quantity().intValue() < max.intValue()) {
					break;
				}
				updatePopularItems(popularItems, ol, i_mapper.get(ol.getOl_i_id()), orderNums);
			}
		}
		//TODO output
	}
	
	private void updatePopularItems(Map<Item, BigDecimal> popularItems, OrderLine ol,
			Item item, Map<Item, Integer> orderNums) {
		if (popularItems.get(item) != null) {
			BigDecimal currentQuantity = popularItems.get(item);
			currentQuantity.add(ol.getOl_quantity());
			popularItems.put(item, currentQuantity);
		} else {
			popularItems.put(item, ol.getOl_quantity());
		}
		
		if (orderNums.get(item) != null) {
			int num = orderNums.get(item);
			orderNums.put(item, num++);
		} else {
			orderNums.put(item, 1);
		}
	}
}
