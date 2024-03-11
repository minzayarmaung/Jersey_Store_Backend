package com.mit.storesystem.Entity;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.jersey.core.header.FormDataContentDisposition;

@XmlRootElement(name = "invoiceAndStocksDTO")
public class InvoiceAndStocksDTO {

	private Long invoiceId;
	private InvoiceRequest invoiceRequest;
	private List<StockRequest> stocks;
	
	// For Image Uploading 
	private InputStream filePath;
	private FormDataContentDisposition contentDisposition;
	
	public InvoiceAndStocksDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvoiceAndStocksDTO(Long invoiceId, InvoiceRequest invoiceRequest, List<StockRequest> stocks, InputStream filePath,
			FormDataContentDisposition contentDisposition) {
		super();
		this.invoiceId = invoiceId;
		this.invoiceRequest = invoiceRequest;
		this.stocks = stocks;
		this.filePath = filePath;
		this.contentDisposition = contentDisposition;
	}
	
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public InvoiceRequest getInvoice() {
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
	
	
	
}
