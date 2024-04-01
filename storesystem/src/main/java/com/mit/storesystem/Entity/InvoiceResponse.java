package com.mit.storesystem.Entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "invoiceResponse")
public class InvoiceResponse {
	
	private Long invoiceId;
	private String cashierName;
	private String date;
	private String time;
	private String branch;
	private String center;
	private String status;
	private String filePath;
	
	public InvoiceResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public InvoiceResponse(Long invoiceId, String cashierName, String date, String time, String branch, String center,
			String status, String filePath) {
		super();
		this.invoiceId = invoiceId;
		this.cashierName = cashierName;
		this.date = date;
		this.time = time;
		this.branch = branch;
		this.center = center;
		this.status = status;
		this.filePath = filePath;
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
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
