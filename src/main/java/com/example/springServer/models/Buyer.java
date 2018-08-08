package com.example.springServer.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Buyer extends User {
	private String address;
	@OneToMany(mappedBy="buyer", orphanRemoval=true)
	@JsonIgnore
	private List<Order> orders;
	@OneToMany(mappedBy="buyer", orphanRemoval=true)
	@JsonIgnore
	private List<Entry> cartItems;
	
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Entry> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<Entry> cartItems) {
		this.cartItems = cartItems;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
