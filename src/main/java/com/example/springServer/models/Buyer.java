package com.example.springServer.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Buyer extends User {
	private String address;
	@OneToMany(mappedBy="buyer", orphanRemoval =true)
	@JsonIgnore
	private List<Product> orders;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
