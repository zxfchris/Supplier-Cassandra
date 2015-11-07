package edu.nus.cs4224;

import com.datastax.driver.core.ConsistencyLevel;

public class Constants {
	
	public static final String NODE = "localhost"; //TODO cluster seed node
	
	public static final int PORT = 9042;
	
	public static final String KEYSPACE = "Supplier";
	
	public static final ConsistencyLevel CL = ConsistencyLevel.ONE;
	
	public static final String COMPG_40 = "192.168.8.134";
	
	public static final String COMPG_41 = "192.168.8.133";
}
