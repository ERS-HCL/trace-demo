package com.tfg.example.domain;

import java.io.Serializable;
import java.util.List;

public class ShoppingCart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5167474377873385334L;

	public ShoppingCart() {
		super();
		this.id = 0;
		this.lineItem = null;
		this.checkedOut = false;

	}

	private final long id;

	private List<LineItem> lineItem;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	private String orderId;

	public void setLineItem(List<LineItem> lineItem) {
		this.lineItem = lineItem;
	}

	private boolean checkedOut;

	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	public boolean isCheckedOut() {
		return checkedOut;
	}

	public List<LineItem> getLineItem() {
		return lineItem;
	}

	public ShoppingCart(long id, List<LineItem> lineItem) {
		super();
		this.id = id;
		this.lineItem = lineItem;
		this.checkedOut = false;
	}

	public long getId() {
		return id;
	}

}
