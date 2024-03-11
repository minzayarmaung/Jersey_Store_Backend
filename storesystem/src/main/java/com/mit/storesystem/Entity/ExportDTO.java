package com.mit.storesystem.Entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "exportDTO")
public class ExportDTO {
	
	private Long invoiceId;
	private String cashierName;
	private String date;
	private String time;
	private String branch;
	private String center;
	private String stockName;
	private float stockPrice;
	private int stockQuantity;
	private float amount;
	
	public ExportDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExportDTO(Long invoiceId, String cashierName, String date, String time, String branch, String center,
			String stockName, float stockPrice, int stockQuantity, float amount) {
		super();
		this.invoiceId = invoiceId;
		this.cashierName = cashierName;
		this.date = date;
		this.time = time;
		this.branch = branch;
		this.center = center;
		this.stockName = stockName;
		this.stockPrice = stockPrice;
		this.stockQuantity = stockQuantity;
		this.amount = amount;
	}
	
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getCashierName() {
		return cashierName;
	}
	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public float getStockPrice() {
		return stockPrice;
	}
	public void setStockPrice(float stockPrice) {
		this.stockPrice = stockPrice;
	}
	public int getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	

}
