package edu.nus.cs4224;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

import edu.nus.cs4224.d8.Order;
import edu.nus.cs4224.d8.OrderLine;

@Accessor
public interface MyAccessor {
	@Query("SELECT * From D8.order_line where "
			+ "ol_w_id = :ol_w_id AND ol_d_id = :ol_d_id "
			+ "AND ol_o_id >= :ol_o_id_l AND ol_o_id < :ol_o_id")
	Result<OrderLine> getLastLOrdersLine(@Param("ol_w_id") int w_id, 
			@Param("ol_d_id") int d_id, @Param("ol_o_id_l") int ol_o_idMinusL,
			@Param("ol_o_id") int ol_o_id);
	
	@Query("SELECT * From D8.order where "
			+ "o_w_id = :o_w_id AND o_d_id = :o_d_id "
			+ "AND o_id >= :o_idMinusL AND o_id < :o_id")
	Result<Order> getLastLOrders(@Param("o_w_id") int w_id, 
			@Param("o_d_id") int d_id, @Param("o_idMinusL") int o_idMinusL,
			@Param("o_id") int o_id);
	
	@Query("SELECT * FROM D8.order_line where "
			+ "ol_w_id = :ol_w_id AND ol_d_id = :ol_d_id"
			+ "AND ol_o_id = :ol_o_id AND ORDER BY ol_quantity DESC")
	Result<OrderLine> getOLbyOrders(@Param("ol_w_id") int w_id, 
			@Param("ol_d_id") int d_id, @Param("ol_o_id") int o_id);
	
}
