package com.mit.storesystem.Entity;

import java.io.InputStream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.jersey.core.header.FormDataContentDisposition;

@XmlRootElement(name = "invoiceAndStockDataRequest")
public class InvoiceAndStockDataRequest {
	
	private Long invoiceId;
	private String cashierName;
	private String date;
	private String time;
	private String branch;
	private String center;
	private Long stockId;
	private String name;
	private float price;
	private int quantity;
	private float amount;
	// For Image Uploading 
	private InputStream filePath;
	private FormDataContentDisposition contentDisposition;
	
	public InvoiceAndStockDataRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public InvoiceAndStockDataRequest(Long invoiceId, String cashierName, String date, String time, String branch,
			String center, Long stockId, String name, float price, int quantity, float amount, InputStream filePath,
			FormDataContentDisposition contentDisposition) {
		super();
		this.invoiceId = invoiceId;
		this.cashierName = cashierName;
		this.date = date;
		this.time = time;
		this.branch = branch;
		this.center = center;
		this.stockId = stockId;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.amount = amount;
		this.filePath = filePath;
		this.contentDisposition = contentDisposition;
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
	public Long getStockId() {
		return stockId;
	}
	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}

	public InputStream getFilePath() {
		return filePath;
	}

	public void setFilePath(InputStream filePath) {
		this.filePath = filePath;
	}

	public FormDataContentDisposition getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(FormDataContentDisposition contentDisposition) {
		this.contentDisposition = contentDisposition;
	}
	
	
	
}
