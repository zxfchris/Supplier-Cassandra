package edu.nus.cs4224;

import java.math.BigDecimal;
import java.util.ArrayList;
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
	
	private static Session session ;
	private static MappingManager manager;
	private static MyAccessor myAccessor;
	private Mapper<District> d_mapper;
	private Mapper<Customer> c_mapper;
	private Mapper<Item> i_mapper;
	
	public Supplier() {
		CassandraConnector connector = new CassandraConnector();
		connector.connect(Constants.NODE, Constants.PORT, Constants.KEYSPACE);
		session = connector.getSession();
		manager = new MappingManager(session);
		//myAccessor = manager.createAccessor(MyAccessor.class);
		d_mapper = manager.mapper(District.class);
		//c_mapper = manager.mapper(Customer.class);
		i_mapper = manager.mapper(Item.class);
	}

	public static void main(String[] args) {
		Supplier supplier = new Supplier();
		supplier.query("warehouse");
		//BigDecimal ytd = new BigDecimal(20);
		//supplier.paymentTran(1, 5, 10, ytd);
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
	
	public void update(String table, String[] names, Object[] values) {
		
	}
	
	public void delete(String table, String key, String value) {
		session.execute(QueryBuilder.delete()
				   .from(table)
				   .where(QueryBuilder.eq(key, value)));
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
		//Order o1 = orderMapper.get("5,1,3");
		//System.out.println(o1.getO_carrier_id());
	}
	
	/**
	 * Stock-level Transaction
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
	 * Popular-Item Transaction
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
		
			Result<OrderLine> o_ols = myAccessor.getOLbyOrders(w_id, d_id, o.getO_id());
			OrderLine ol = o_ols.iterator().next();
			BigDecimal max = ol.getOl_quantity();
			Item item = i_mapper.get(ol.getOl_i_id());
			updatePopularItems(popularItems, ol, item, orderNums);
			while ((ol = o_ols.iterator().next()) != null) {
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
