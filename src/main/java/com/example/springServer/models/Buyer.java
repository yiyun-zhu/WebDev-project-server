package com.example.springServer.models;

import javax.persistence.Entity;

@Entity
public class Buyer extends Person{
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
