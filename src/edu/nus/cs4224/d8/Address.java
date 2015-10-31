package edu.nus.cs4224.d8;

public class Address {
	private String address_1;
	private String address_2;
	private String city;
	private String state;
	private String zip;
	
	public Address(){
	}
	
	public Address(String address_1, String address_2, 
			String city, String state, String zip) {
		this.address_1 = address_1;
		this.address_2 = address_2;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public String getAddress_1() {
		return address_1;
	}
	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}
	public String getAddress_2() {
		return address_2;
	}
	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
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
}
