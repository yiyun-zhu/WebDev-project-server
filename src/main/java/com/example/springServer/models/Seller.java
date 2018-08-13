package com.example.springServer.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Seller extends User {
	private String address;
	@OneToMany(mappedBy="seller", orphanRemoval =true)
	@JsonIgnore
	private List<Product> product;
	@ManyToMany(mappedBy="sellers")
	@JsonIgnore
	private List<Buyer> buyers;
	
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
