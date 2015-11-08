package edu.nus.cs4224;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class CassandraConnector {
	
	private Cluster cluster;
	
	private Session session;
	
	/**
    * Connect to Cassandra Cluster specified by provided node IP
    * address and port number.
    *
    * @param node Cluster node IP address.
    * @param port Port of cluster host.
    * @param keyspace Keysapce Name
    */
   public void connect(final String node, final int port, final String keyspace) {
//      this.cluster = Cluster.builder().addContactPoint(node).withPort(port).build();
	  this.cluster = Cluster.builder().addContactPoint("localhost").build();
//	  this.cluster = Cluster.builder().addContactPoint(Constants.COMPG_40).addContactPoint(Constants.COMPG_41).build();
      final Metadata metadata = cluster.getMetadata();
      System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
      for (final Host host : metadata.getAllHosts())
      {
         System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
            host.getDatacenter(), host.getAddress(), host.getRack());
      }
      session = cluster.connect(keyspace);
   }
   
   /** Close cluster. */
   public void close() {
      cluster.close();
   }
   
   public Session getSession() {
	   return this.session;
   }
}
