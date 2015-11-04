package edu.nus.cs4224.d8;

import java.math.BigDecimal;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "D8", name = "district")
public class District {
	@PartitionKey(0)
	private int d_w_id;
	@ClusteringColumn
	private int d_id;
	
	private String d_name;
	private String d_street_1;
	private String d_street_2;
	private String d_city;
	private String d_state;
	private String d_zip;
	private BigDecimal d_tax;
	private BigDecimal d_ytd;
	private int d_next_o_id;
	
	public District(){
	}
	
	public District(int w_id, int d_id, String name, String street_1, String street_2,
			String city, String state, String zip, BigDecimal tax, BigDecimal ytd, int next_o_id) {
		this.d_w_id = w_id;
		this.setD_id(d_id);
		this.setD_name(name);
		this.setD_street_1(street_1);
		this.setD_street_2(street_2);
		this.setD_city(city);
		this.setD_state(state);
		this.setD_zip(zip);
		this.setD_tax(tax);
		this.setD_ytd(ytd);
		this.setD_next_o_id(next_o_id);
	}

	public int getD_w_id() {
		return d_w_id;
	}

	public void setD_w_id(int d_w_id) {
		this.d_w_id = d_w_id;
	}

	public int getD_id() {
		return d_id;
	}

	public void setD_id(int d_id) {
		this.d_id = d_id;
	}

	public String getD_name() {
		return d_name;
	}

	public void setD_name(String d_name) {
		this.d_name = d_name;
	}

	public String getD_street_1() {
		return d_street_1;
	}

	public void setD_street_1(String d_street_1) {
		this.d_street_1 = d_street_1;
	}

	public String getD_street_2() {
		return d_street_2;
	}

	public void setD_street_2(String d_street_2) {
		this.d_street_2 = d_street_2;
	}

	public String getD_city() {
		return d_city;
	}

	public void setD_city(String d_city) {
		this.d_city = d_city;
	}

	public String getD_state() {
		return d_state;
	}

	public void setD_state(String d_state) {
		this.d_state = d_state;
	}

	public String getD_zip() {
		return d_zip;
	}

	public void setD_zip(String d_zip) {
		this.d_zip = d_zip;
	}

	public BigDecimal getD_tax() {
		return d_tax;
	}

	public void setD_tax(BigDecimal d_tax) {
		this.d_tax = d_tax;
	}

	public BigDecimal getD_ytd() {
		return d_ytd;
	}

	public void setD_ytd(BigDecimal d_ytd) {
		this.d_ytd = d_ytd;
	}

	public int getD_next_o_id() {
		return d_next_o_id;
	}

	public void setD_next_o_id(int d_next_o_id) {
		this.d_next_o_id = d_next_o_id;
	}

}
