package edu.nus.cs4224.d8;

import java.math.BigDecimal;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "D8", name = "warehouse")
public class Warehouse {
	@PartitionKey
	@Column(name="w_id")
	private int id;
	private String w_name;
	private String w_street_1;
	private String w_street_2;
	private String w_city;
	private String w_state;
	private String w_zip;
	private BigDecimal w_tax;
	private BigDecimal w_ytd;
	
	public Warehouse() {
	}
	
	public Warehouse(int id, String name, String street_1, String street_2, String city, 
			String state, String zip, BigDecimal tax, BigDecimal ytd) {
		this.id = id;
		this.setW_name(name);
		this.setW_street_1(street_1);
		this.setW_street_2(street_2);
		this.setW_city(city);
		this.setW_state(state);
		this.setW_zip(zip);
		this.setW_tax(tax);
		this.setW_ytd(ytd);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getW_name() {
		return w_name;
	}

	public void setW_name(String w_name) {
		this.w_name = w_name;
	}

	public String getW_street_1() {
		return w_street_1;
	}

	public void setW_street_1(String w_street_1) {
		this.w_street_1 = w_street_1;
	}

	public String getW_street_2() {
		return w_street_2;
	}

	public void setW_street_2(String w_street_2) {
		this.w_street_2 = w_street_2;
	}

	public String getW_city() {
		return w_city;
	}

	public void setW_city(String w_city) {
		this.w_city = w_city;
	}

	public String getW_state() {
		return w_state;
	}

	public void setW_state(String w_state) {
		this.w_state = w_state;
	}

	public String getW_zip() {
		return w_zip;
	}

	public void setW_zip(String w_zip) {
		this.w_zip = w_zip;
	}

	public BigDecimal getW_tax() {
		return w_tax;
	}

	public void setW_tax(BigDecimal w_tax) {
		this.w_tax = w_tax;
	}

	public BigDecimal getW_ytd() {
		return w_ytd;
	}

	public void setW_ytd(BigDecimal w_ytd) {
		this.w_ytd = w_ytd;
	}
	
}
