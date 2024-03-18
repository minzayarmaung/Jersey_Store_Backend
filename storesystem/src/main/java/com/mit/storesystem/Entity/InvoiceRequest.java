package com.mit.storesystem.Entity;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "invoiceRequest")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceRequest  {
	
	private Long invoiceId;
	private String cashierName;
	private String branch;
	private String date;
	private String time;
	private String center;
	private String status;
	private String filePath;
	
	public InvoiceRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public InvoiceRequest(Long invoiceId, String cashierName, String branch, String date, String time, String center,
			String status) {
		super();
		this.invoiceId = invoiceId;
		this.cashierName = cashierName;
		this.branch = branch;
		this.date = date;
		this.time = time;
		this.center = center;
		this.status = status;
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
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
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
	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
	
}
