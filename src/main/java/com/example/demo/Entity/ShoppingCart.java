package com.example.demo.Entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
@Document(collection = "Shoppingcart")
public class ShoppingCart {
	@Id
	private int id;
	
	@NotNull(message = "Username is required")
	private String userName;
	private List<String> items;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return userName;
	}
	public void setUsername(String username) {
		this.userName = username;
	}
	public List<String> getItems() {
		return items;
	}
	public void setItems(List<String> items) {
		this.items = items;
	}
	public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    // Constructor with arguments
    public ShoppingCart(int id, @NotNull(message = "Username is required") String userName, List<String> items) {
        this.id = id;
        this.userName = userName;
        this.items = (items != null) ? items : new ArrayList<>();
    }
    public ShoppingCart(String userName, List<String> items) {
        this.userName = userName;
        this.items = (items != null) ? items : new ArrayList<>();
    }
	@Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", username=" + userName + ", items=" + items + ", getId()=" + getId()
				+ ", getUsername()=" + getUsername() + ", getItems()=" + getItems() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	

}
