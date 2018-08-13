package com.example.springServer.models;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Seller extends User {
	private String address;
	@OneToMany(mappedBy="seller", orphanRemoval =true)
	@JsonIgnore
	private List<Product> product;
	@ElementCollection
	private List<Integer> buyerIds;
	
	public List<Integer> getBuyerIds() {
		return buyerIds;
	}

	public void setBuyerIds(List<Integer> buyerIds) {
		this.buyerIds = buyerIds;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProducts(List<Product> product) {
		this.product = product;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
