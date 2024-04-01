package com.mit.storesystem.Entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "updateStock")
public class StockUpdate {
	
	private Long stockId;
	private String name;
	private int quantity;
	private float price;
	private float amount;
	
	public StockUpdate() {
		// TODO Auto-generated constructor stub
	}

	public StockUpdate(Long stockId, String name, int quantity, float price, float amount) {
		super();
		this.stockId = stockId;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.amount = amount;
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
		this.amount = this.price * this.quantity;
	}
	
	
	

}
