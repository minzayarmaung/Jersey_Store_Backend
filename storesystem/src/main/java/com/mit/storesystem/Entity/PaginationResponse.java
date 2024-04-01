package com.mit.storesystem.Entity;

import java.util.List;

public class PaginationResponse {
	private List<InvoiceAndStockDataResponse> items;
	private int totalCount;
	private int pages;
		
	public PaginationResponse(List<InvoiceAndStockDataResponse> items, int totalCount, int pages) {
		super();
		this.items = items;
		this.totalCount = totalCount;
		this.pages = pages;
	}
	
	public List<InvoiceAndStockDataResponse> getItems() {
		return items;
	}
	public void setItems(List<InvoiceAndStockDataResponse> items) {
		this.items = items;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	
	

}
