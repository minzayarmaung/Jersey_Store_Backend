package com.mit.storesystem.Entity;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement(name = "stockRequest")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockRequest {
	
	private Long stockId;
	
	private InvoiceRequest invoiceRequest;
	private String name;
	private int quantity;
	private float price;
	private float amount;
	private String status;
	private Long invoiceId;
	
	public StockRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StockRequest(Long stockId, InvoiceRequest invoiceRequest, String name, int quantity, float price,
			float amount, String status, Long invoiceId) {
		super();
		this.stockId = stockId;
		this.invoiceRequest = invoiceRequest;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.amount = amount;
		this.status = status;
		this.invoiceId = invoiceId;
	}

	public Long getStockId() {
		return stockId;
	}
	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}
	public InvoiceRequest getInvoice() {
		return invoiceRequest;
	}
	public void setInvoice(InvoiceRequest invoiceRequest) {
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
	public InvoiceRequest getInvoiceRequest() {
		return invoiceRequest;
	}
	public void setInvoiceRequest(InvoiceRequest invoiceRequest) {
		this.invoiceRequest = invoiceRequest;
	}
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	

}
