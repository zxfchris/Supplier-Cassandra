package edu.nus.cs4224;

import java.math.BigDecimal;

import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

import edu.nus.cs4224.d8.District;
import edu.nus.cs4224.d8.Order;
import edu.nus.cs4224.d8.OrderByCustomer;
import edu.nus.cs4224.d8.OrderLine;
import edu.nus.cs4224.d8.Stock;

@Accessor
public interface MyAccessor {
	@Query("UPDATE supplier.district set d_next_o_id= :d_next_o_id where d_w_id= :d_w_id and d_id = :d_id")
	Statement updateNextOrderId(@Param("d_w_id") int w_id, @Param("d_id") int d_id, @Param("d_next_o_id")int d_next_o_id);
	
	@Query("UPDATE supplier.stock set s_quantity=:adjusted_quantity, s_ytd=:ytd_quantity, "
			+ "s_order_cnt=:s_order_cnt where s_w_id=:w_id and s_i_id=:i_id")
	Statement updateLocalStock(@Param("adjusted_quantity")BigDecimal adjusted_quantity, 
			@Param("ytd_quantity") BigDecimal ytd_quantity, @Param("w_id") int w_id, 
			@Param("i_id") int i_id, @Param("s_order_cnt") int s_order_cnt);
	
	@Query("UPDATE supplier.stock set s_quantity=:adjusted_quantity, s_ytd=:ytd_quantity, "
			+ "s_order_cnt=:s_order_cnt, s_remote_cnt=:s_remote_cnt where s_w_id=:w_id and s_i_id=:i_id")
	Statement updateRemoteStock(@Param("adjusted_quantity")BigDecimal adjusted_quantity, 
			@Param("ytd_quantity") BigDecimal ytd_quantity, @Param("w_id") int w_id, @Param("i_id") int i_id,
			@Param("s_order_cnt") int s_order_cnt, @Param("s_remote_cnt") int s_remote_cnt);
	
	@Query("SELECT * From supplier.order_line where "
			+ "ol_w_id = :ol_w_id AND ol_d_id = :ol_d_id "
			+ "AND ol_o_id >= :ol_o_id_l AND ol_o_id < :ol_o_id")
	Result<OrderLine> getLastLOrdersLine(@Param("ol_w_id") int w_id, 
			@Param("ol_d_id") int d_id, @Param("ol_o_id_l") int ol_o_idMinusL,
			@Param("ol_o_id") int ol_o_id);
	
	@Query("SELECT * From supplier.order_ where "
			+ " o_w_id = :o_w_id  AND o_d_id = :o_d_id "
			+ "AND o_id >= :o_idMinusL AND o_id < :o_id")
	Result<Order> getLastLOrders(@Param("o_w_id") int w_id, 
			@Param("o_d_id") int d_id, @Param("o_idMinusL") int o_idMinusL,
			@Param("o_id") int o_id);
	
	@Query("SELECT * FROM supplier.order_line where "
			+ "ol_w_id = :ol_w_id AND ol_d_id = :ol_d_id"
			+ " AND ol_o_id = :ol_o_id")			
	Result<OrderLine> getOrderLinesByOrder(@Param("ol_w_id") int w_id, 
			@Param("ol_d_id") int d_id, @Param("ol_o_id") int o_id);
	
	//transaction 3, delivery
	@Query("SELECT * FROM supplier.district where " + "d_w_id = :d_w_id")
	Result<District> getDistrictByWid(@Param("d_w_id") int w_id);
	
	//+ "order by o_id")	problem here, order by not work
//	@Query("SELECT * FROM D8.order_by_carrier where " 
//			+ "o_w_id = :o_w_id AND o_d_id = :o_d_id AND o_carrier_id = 0 order by o_id")
//	Result<OrderByCarrier> getOrderByDistrictDelivery(@Param("o_w_id") int w_id,
//			@Param("o_d_id") int d_id);
	@Query("SELECT * FROM supplier.order_ where " 
			+ "o_w_id = :o_w_id AND o_d_id = :o_d_id AND o_carrier_id = 0 limit 1")
	Result<Order> getOrderByDistrictDelivery(@Param("o_w_id") int w_id,
			@Param("o_d_id") int d_id);
	
	//transaction 4, order-status
	@Query("SELECT * FROM supplier.order_by_customer where " 
			+ "o_w_id = :o_w_id AND o_d_id = :o_d_id AND o_c_id = :o_c_id"
			+ " order by o_id desc")
	Result<OrderByCustomer> getOrderByCustomer(@Param("o_w_id") int w_id,
			@Param("o_d_id") int d_id, @Param("o_c_id") int c_id);
	
	@Query("SELECT * FORM supplier.stock where s_w_id = :s_w_id and "
			+ "s_i_id >= :min_i_id and s_i_id <= :max_i_id")
	Result<Stock> getStockByItemRange(@Param("s_w_id") int s_w_id, 
			@Param("min_i_id") int min_i_id, @Param("max_i_id") int max_i_id);
}
