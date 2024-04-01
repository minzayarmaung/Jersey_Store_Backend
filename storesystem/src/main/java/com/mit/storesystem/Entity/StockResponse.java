package com.mit.storesystem.Entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stockResponse")
public class StockResponse {
	
	private Long stockId;
	private Long invoiceId;
	private InvoiceRequest invoiceRequest;
	private String name;
	private int quantity;
	private float price;
	private float amount;
	private String status;
	
	public StockResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public StockResponse(Long stockId) {
		super();
		this.stockId = stockId;
	}



	public StockResponse(Long stockId, Long invoiceId, String name, int quantity, float price, float amount,
			String status) {
		super();
		this.stockId = stockId;
		this.invoiceId = invoiceId;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.amount = amount;
		this.status = status;
	}



	public StockResponse(Long stockId, Long invoiceId, InvoiceRequest invoiceRequest, String name, int quantity,
			float price, float amount, String status) {
		super();
		this.stockId = stockId;
		this.invoiceId = invoiceId;
		this.invoiceRequest = invoiceRequest;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.amount = amount;
		this.status = status;
	}

	public Long getStockId() {
		return stockId;
	}
	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}
	
	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public InvoiceRequest getInvoiceRequest() {
		return invoiceRequest;
	}
	public void setInvoiceRequest(InvoiceRequest invoiceRequest) {
		this.invoiceRequest = invoiceRequest;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
