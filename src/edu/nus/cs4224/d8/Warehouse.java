package edu.nus.cs4224.d8;

import java.math.BigDecimal;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "D8", name = "warehouse")
public class Warehouse {
	@PartitionKey
	private int id;
	private String name;
	private String street_1;
	private String street_2;
	private String city;
	private String state;
	private String zip;
	private BigDecimal tax;
	private BigDecimal ytd;
	
	public Warehouse() {
	}
	
	public Warehouse(int id, String name, String street_1, String street_2, String city, 
			String state, String zip, BigDecimal tax, BigDecimal ytd) {
		this.id = id;
		this.name = name;
		this.street_1 = street_1;
		this.street_2 = street_2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.tax = tax;
		this.ytd = ytd;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	
}
