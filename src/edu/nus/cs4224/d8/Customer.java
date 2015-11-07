package edu.nus.cs4224.d8;

import java.math.BigDecimal;
import java.util.Date;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "Supplier", name = "customer")
public class Customer {
	@PartitionKey(0)
	private int c_w_id;
	@PartitionKey(1)
	private int c_d_id;
	@ClusteringColumn(0)
	private int c_id;
	private String c_first;
	private String c_middle;
	private String c_last;
	private String c_street_1;
	private String c_street_2;
	private String c_city;
	private String c_state;
	private String c_zip;
	private String c_phone;
	private Date c_since;
	private String c_credit;
	private BigDecimal c_credit_lim;
	private BigDecimal c_discount;
	private BigDecimal c_balance;
	private float c_ytd_payment;
	private int c_payment_cnt;
	private int c_delivery_cnt;
	private String c_data;
	
	public Customer() {
	}
	
	public Customer(int c_w_id, int c_d_id, int c_id) {
		this.c_w_id = c_w_id;
		this.c_d_id = c_d_id;
		this.c_id = c_id;
	}
	
	public int getC_w_id() {
		return c_w_id;
	}
	public void setC_w_id(int c_w_id) {
		this.c_w_id = c_w_id;
	}
	public int getC_d_id() {
		return c_d_id;
	}
	public void setC_d_id(int c_d_id) {
		this.c_d_id = c_d_id;
	}
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public String getC_first() {
		return c_first;
	}
	public void setC_first(String c_first) {
		this.c_first = c_first;
	}
	public String getC_middle() {
		return c_middle;
	}
	public void setC_middle(String c_middle) {
		this.c_middle = c_middle;
	}
	public String getC_last() {
		return c_last;
	}
	public void setC_last(String c_last) {
		this.c_last = c_last;
	}
	public String getC_street_1() {
		return c_street_1;
	}
	public void setC_street_1(String c_street_1) {
		this.c_street_1 = c_street_1;
	}
	public String getC_street_2() {
		return c_street_2;
	}
	public void setC_street_2(String c_street_2) {
		this.c_street_2 = c_street_2;
	}
	public String getC_city() {
		return c_city;
	}
	public void setC_city(String c_city) {
		this.c_city = c_city;
	}
	public String getC_state() {
		return c_state;
	}
	public void setC_state(String c_state) {
		this.c_state = c_state;
	}
	public String getC_zip() {
		return c_zip;
	}
	public void setC_zip(String c_zip) {
		this.c_zip = c_zip;
	}
	public String getC_phone() {
		return c_phone;
	}
	public void setC_phone(String c_phone) {
		this.c_phone = c_phone;
	}
	public Date getC_since() {
		return c_since;
	}
	public void setC_since(Date c_since) {
		this.c_since = c_since;
	}
	public String getC_credit() {
		return c_credit;
	}
	public void setC_credit(String c_credit) {
		this.c_credit = c_credit;
	}
	public BigDecimal getC_credit_lim() {
		return c_credit_lim;
	}
	public void setC_credit_lim(BigDecimal c_credit_lim) {
		this.c_credit_lim = c_credit_lim;
	}
	public BigDecimal getC_discount() {
		return c_discount;
	}
	public void setC_discount(BigDecimal c_discount) {
		this.c_discount = c_discount;
	}
	public BigDecimal getC_balance() {
		return c_balance;
	}
	public void setC_balance(BigDecimal c_balance) {
		this.c_balance = c_balance;
	}
	public float getC_ytd_payment() {
		return c_ytd_payment;
	}
	public void setC_ytd_payment(float c_ytd_payment) {
		this.c_ytd_payment = c_ytd_payment;
	}
	public int getC_payment_cnt() {
		return c_payment_cnt;
	}
	public void setC_payment_cnt(int c_payment_cnt) {
		this.c_payment_cnt = c_payment_cnt;
	}
	public int getC_delivery_cnt() {
		return c_delivery_cnt;
	}
	public void setC_delivery_cnt(int c_delivery_cnt) {
		this.c_delivery_cnt = c_delivery_cnt;
	}
	public String getC_data() {
		return c_data;
	}
	public void setC_data(String c_data) {
		this.c_data = c_data;
	}
}
