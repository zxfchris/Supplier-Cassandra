package edu.nus.cs4224.dao;

import java.math.BigDecimal;

//data transfer object for a warehouse
public class Warehouse {
	int id;
	String name;
	String street_1;
	String street_2;
	String city;
	String state;
	String zip;
	BigDecimal tax;
	BigDecimal ytd;
}
