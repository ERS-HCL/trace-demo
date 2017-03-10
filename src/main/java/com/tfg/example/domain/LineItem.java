package com.tfg.example.domain;

import java.io.Serializable;

public class LineItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4893983370781391839L;
	private final long id;
	private final String sku;
	
	public LineItem() {
		super();
		this.id = 0;
		this.sku = "";
		// TODO Auto-generated constructor stub
	}
	public LineItem(long id, String sku) {
		super();
		this.id = id;
		this.sku = sku;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineItem other = (LineItem) obj;
		if (id != other.id)
			return false;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.equals(other.sku))
			return false;
		return true;
	}

	public long getId() {
		return id;
	}

	public String getSku() {
		return sku;
	}
	
}
