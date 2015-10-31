package edu.nus.cs4224.d8;

import java.math.BigDecimal;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "D8", name = "district")
public class District {
	@PartitionKey(0)
	private int w_id;
	@PartitionKey(1)
	private int d_id;
	
	private String name;
	private String street_1;
	private String street_2;
	private String city;
	private String state;
	private String zip;
	private BigDecimal tax;
	private BigDecimal ytd;
	private int next_o_id;
	
	public District(){
	}

	public District(int w_id, int d_id, String name, String street_1, String street_2,
			String city, String state, String zip, BigDecimal tax, BigDecimal ytd, int next_o_id) {
		this.w_id = w_id;
		this.d_id = d_id;
		this.name = name;
		this.street_1 = street_1;
		this.street_2 = street_2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.tax = tax;
		this.ytd = ytd;
		this.next_o_id = next_o_id;
	}
	
	public int getW_id() {
		return w_id;
	}
	public void setW_id(int w_id) {
		this.w_id = w_id;
	}
	public int getD_id() {
		return d_id;
	}
	public void setD_id(int d_id) {
		this.d_id = d_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet_1() {
		return street_1;
	}
	public void setStreet_1(String street_1) {
		this.street_1 = street_1;
	}
	public String getStreet_2() {
		return street_2;
	}
	public void setStreet_2(String street_2) {
		this.street_2 = street_2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getYtd() {
		return ytd;
	}
	public void setYtd(BigDecimal ytd) {
		this.ytd = ytd;
	}
	public int getNext_o_id() {
		return next_o_id;
	}
	public void setNext_o_id(int next_o_id) {
		this.next_o_id = next_o_id;
	}
}
