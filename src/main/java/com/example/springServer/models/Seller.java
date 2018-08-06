package com.example.springServer.models;

import javax.persistence.Entity;

@Entity
public class Seller extends Person {
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
