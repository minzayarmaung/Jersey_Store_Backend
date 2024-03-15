package com.mit.storesystem.Entity;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.sun.jersey.core.header.FormDataContentDisposition;

@XmlRootElement(name = "invoiceAndStocksDTO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceAndStocksDTO {

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
	
	// For Image Uploading 
	private InputStream filePath;
	private FormDataContentDisposition contentDisposition;
	
	public InvoiceAndStocksDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvoiceAndStocksDTO(Long invoiceId, String cashierName, String date, String time, String branch,
			String center, Long stockId, String name, float price, int quantity, float amount, String status,
			InvoiceAndStocksDTO invoiceRequest, List<StockRequest> stocks, InputStream filePath,
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
		this.status = status;
		this.invoiceRequest = invoiceRequest;
		this.stocks = stocks;
		this.filePath = filePath;
		this.contentDisposition = contentDisposition;
	}

	
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}
	@JsonProperty("invoice")
	public void setInvoice(InvoiceAndStocksDTO invoiceRequest) {
	    this.invoiceRequest = invoiceRequest;
	}

	public InvoiceAndStocksDTO getInvoice() {
		return invoiceRequest;
	}
	public void setInvoice(InvoiceRequest invoice) {
		this.invoiceId = invoiceId;
	}
	public List<StockRequest> getStocks() {
		return stocks;
	}
	public void setStocks(List<StockRequest> stocks) {
		this.stocks = stocks;
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

	
	
	
}
