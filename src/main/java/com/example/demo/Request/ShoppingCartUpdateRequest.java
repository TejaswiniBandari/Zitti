package com.example.demo.Request;

import java.util.List;

public class ShoppingCartUpdateRequest {
	private List<String> items;
    private boolean remove;
	public List<String> getItems() {
		return items;
	}
	public void setItems(List<String> items) {
		this.items = items;
	}
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	public ShoppingCartUpdateRequest(List<String> items, boolean remove) {
		super();
		this.items = items;
		this.remove = remove;
	}
	@Override
	public String toString() {
		return "ShoppingCartUpdateRequest [items=" + items + ", remove=" + remove + "]";
	}
    
    
}
