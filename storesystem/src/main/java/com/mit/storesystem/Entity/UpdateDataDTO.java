package com.mit.storesystem.Entity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "updateDataDTO")
public class UpdateDataDTO {
	
	private InvoiceRequest updatedInvoice;
	private List<StockRequest> updatedStocks;

	public UpdateDataDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UpdateDataDTO(InvoiceRequest updatedInvoice, List<StockRequest> updatedStocks) {
		super();
		this.updatedInvoice = updatedInvoice;
		this.updatedStocks = updatedStocks;
	}
	
	public InvoiceRequest getUpdatedInvoice() {
		return updatedInvoice;
	}
	public void setUpdatedInvoice(InvoiceRequest updatedInvoice) {
		this.updatedInvoice = updatedInvoice;
	}
	public List<StockRequest> getUpdatedStocks() {
		return updatedStocks;
	}
	public void setUpdatedStocks(List<StockRequest> updatedStocks) {
		this.updatedStocks = updatedStocks;
	}
	
	

}
