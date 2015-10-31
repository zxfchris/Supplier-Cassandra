package edu.nus.cs4224.d8;

import java.math.BigDecimal;
import java.util.Date;

import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "D8", name = "customer")
public class Customer {
	private int w_id;
	private int d_id;
	private int c_id;
	
	private Address address;
	
	private String phone;
	private Date since;
	private String credit;
	private BigDecimal credit_lim;
	private BigDecimal discount;
	private BigDecimal balance;
	private float ytd_payment;
	private int payment_cnt;
	private int delivery_cnt;
	private String data;
	
	public Customer() {
	}
	
	public Customer(int w_id, int d_id, int c_id) {
		this.w_id = w_id;
		this.d_id = d_id;
		this.c_id = c_id;
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
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Date getSince() {
		return since;
	}
	public void setSince(Date since) {
		this.since = since;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public BigDecimal getCredit_lim() {
		return credit_lim;
	}
	public void setCredit_lim(BigDecimal credit_lim) {
		this.credit_lim = credit_lim;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public float getYtd_payment() {
		return ytd_payment;
	}
	public void setYtd_payment(float ytd_payment) {
		this.ytd_payment = ytd_payment;
	}
	public int getPayment_cnt() {
		return payment_cnt;
	}
	public void setPayment_cnt(int payment_cnt) {
		this.payment_cnt = payment_cnt;
	}
	public int getDelivery_cnt() {
		return delivery_cnt;
	}
	public void setDelivery_cnt(int delivery_cnt) {
		this.delivery_cnt = delivery_cnt;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
