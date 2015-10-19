package edu.nus.cs4224;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class Supplier {
	
	private Session session ;
	
	public Supplier() {
		CassandraConnector connector = new CassandraConnector();
		connector.connect(Constants.NODE, Constants.PORT, Constants.KEYSPACE);
		session = connector.getSession();
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
		ResultSet results = session.execute("SELECT CQL"); //TODO
		for (Row row : results) {
			
		}
	}
}
