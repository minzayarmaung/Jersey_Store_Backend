package com.mit.storesystem.Entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceAndStocksDTOWithoutFiles {
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
	private String status;
	
	@JsonProperty("invoice")
	private InvoiceAndStocksDTO invoiceRequest;
	private List<StockRequest> stocks;
	
	public InvoiceAndStocksDTOWithoutFiles() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InvoiceAndStocksDTOWithoutFiles(Long invoiceId, String cashierName, String date, String time, String branch,
			String center, Long stockId, String name, float price, int quantity, float amount, String status,
			InvoiceAndStocksDTO invoiceRequest, List<StockRequest> stocks) {
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
		this.status = status;
		this.invoiceRequest = invoiceRequest;
		this.stocks = stocks;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public InvoiceAndStocksDTO getInvoiceRequest() {
		return invoiceRequest;
	}
	public void setInvoiceRequest(InvoiceAndStocksDTO invoiceRequest) {
		this.invoiceRequest = invoiceRequest;
	}
	public List<StockRequest> getStocks() {
		return stocks;
	}
	public void setStocks(List<StockRequest> stocks) {
		this.stocks = stocks;
	}
	
	
	
}
