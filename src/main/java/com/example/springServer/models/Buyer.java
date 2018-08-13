package com.example.springServer.models;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Buyer extends User {
	private String address;
	@OneToMany(mappedBy="buyer", orphanRemoval=true)
	@JsonIgnore
	private List<Orders> order;
	@OneToMany(mappedBy="buyer", orphanRemoval=true)
	@JsonIgnore
	private List<Entry> cartItems;
	@ElementCollection
	private List<Integer> sellerIds;

	public List<Integer> getSellerIds() {
		return sellerIds;
	}

	public void setSellerIds(List<Integer> sellerIds) {
		this.sellerIds = sellerIds;
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
	public List<Orders> getOrder() {
		return order;
	}
	public void setOrder(List<Orders> order) {
		this.order = order;
	}

}
