package edu.nus.cs4224;

import com.datastax.driver.core.ConsistencyLevel;

public class Constants {
	
	public static final String NODE = "localhost"; //TODO cluster seed node
	
	public static final int PORT = 9042;
	
	public static final String KEYSPACE = "Supplier";
	
	public static final ConsistencyLevel CL = ConsistencyLevel.ONE;

}
