package edu.nus.cs4224.d8;

import java.math.BigDecimal;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import java.util.Date;

@Table(keyspace = "D8", name = "order_")
public class Order {
	@PartitionKey
	private int o_w_id;
    private int o_d_id;
    private int o_id;
    private BigDecimal o_all_local;
    private int o_c_id;
    private int o_carrier_id;
    private Date o_entry_d;
    private BigDecimal o_ol_cnt;
	public int getO_w_id() {
		return o_w_id;
	}
	public void setO_w_id(int o_w_id) {
		this.o_w_id = o_w_id;
	}
	public int getO_d_id() {
		return o_d_id;
	}
	public void setO_d_id(int o_d_id) {
		this.o_d_id = o_d_id;
	}
	public int getO_id() {
		return o_id;
	}
	public void setO_id(int o_id) {
		this.o_id = o_id;
	}
	public BigDecimal getO_all_local() {
		return o_all_local;
	}
	public void setO_all_local(BigDecimal o_all_local) {
		this.o_all_local = o_all_local;
	}
	public int getO_c_id() {
		return o_c_id;
	}
	public void setO_c_id(int o_c_id) {
		this.o_c_id = o_c_id;
	}
	public int getO_carrier_id() {
		return o_carrier_id;
	}
	public void setO_carrier_id(int o_carrier_id) {
		this.o_carrier_id = o_carrier_id;
	}
	public Date getO_entry_d() {
		return o_entry_d;
	}
	public void setO_entry_d(Date o_entry_d) {
		this.o_entry_d = o_entry_d;
	}
	public BigDecimal getO_ol_cnt() {
		return o_ol_cnt;
	}
	public void setO_ol_cnt(BigDecimal o_ol_cnt) {
		this.o_ol_cnt = o_ol_cnt;
	}
}
