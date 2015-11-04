package edu.nus.cs4224.d8;

import java.math.BigDecimal;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "D8", name="item")
public class Item {
	@PartitionKey
	@Column(name="i_id")
	private int i_id;
	private String i_name;
	private BigDecimal i_price;
	private int i_im_id;
	private String i_data;
	
	public Item(){
	}
	public Item(int i_id) {
		this.i_id = i_id;
	}
	
	public int getI_id() {
		return i_id;
	}
	public void setI_id(int i_id) {
		this.i_id = i_id;
	}
	public String getI_name() {
		return i_name;
	}
	public void setI_name(String i_name) {
		this.i_name = i_name;
	}
	public BigDecimal getI_price() {
		return i_price;
	}
	public void setI_price(BigDecimal i_price) {
		this.i_price = i_price;
	}
	public int getI_im_id() {
		return i_im_id;
	}
	public void setI_im_id(int i_im_id) {
		this.i_im_id = i_im_id;
	}
	public String getI_data() {
		return i_data;
	}
	public void setI_data(String i_data) {
		this.i_data = i_data;
	}
}
