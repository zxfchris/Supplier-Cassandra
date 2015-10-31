package edu.nus.cs4224;

import java.math.BigDecimal;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.MappingManager;

public class Supplier {
	
	private Session session ;
	
	public Supplier() {
		CassandraConnector connector = new CassandraConnector();
		connector.connect(Constants.NODE, Constants.PORT, Constants.KEYSPACE);
		session = connector.getSession();
	}

	public static void main(String[] args) {
		Supplier supplier = new Supplier();
		supplier.query("warehouse");
	}
	
	public void paymentTran(int w_id, int d_id, int c_id, BigDecimal paymentAmout) {
		MappingManager manager = new MappingManager(session);
	}
	
	public void insert(String table, String[] names, Object[] values) {
		
		session.execute(QueryBuilder.insertInto(table).values(names, values));
		
	}
	
	public void delete(String table, String key, String value) {
		session.execute(QueryBuilder.delete()
				   .from(table)
				   .where(QueryBuilder.eq(key, value)));
	}
	
	public void query(String table) {
		ResultSet results = session.execute("select * from supplier.warehouse;"); //TODO
		for (Row row : results) {
			System.out.println(row.getInt("w_id"));
		}
	}
}
