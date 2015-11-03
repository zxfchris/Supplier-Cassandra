package edu.nus.cs4224;

import java.math.BigDecimal;

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
	
	public Supplier() {
		CassandraConnector connector = new CassandraConnector();
		connector.connect(Constants.NODE, Constants.PORT, Constants.KEYSPACE);
		session = connector.getSession();
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
		MappingManager manager = new MappingManager(session);
		Mapper<Warehouse> mapper = manager.mapper(Warehouse.class);
		Warehouse wh = new Warehouse( 9, "exdfaefasdfas", "", "", "", 
				"", "", null, null);
		//mapper.save(wh);
		Warehouse w1 = mapper.get(1);
		System.out.println(w1.getW_name());
		
		Mapper<District> distrMapper = manager.mapper(District.class);
		District d1 = distrMapper.get(1,5);
		System.out.println(d1.getD_name());
		
//		Mapper<Order> orderMapper = manager.mapper(Order.class);
//		Order o1 = orderMapper.get("5,1,3");
//		System.out.println(o1.getO_carrier_id());
	}
}
