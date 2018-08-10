package com.example.springServer.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int price;
	private int amount;
	private String movieId;
	@ManyToOne
	private Seller seller;
	@OneToMany(mappedBy="product", orphanRemoval =true)
	@JsonIgnore
	private List<Entry> entry;
	private String title;
	private int initialAmount;
	
	public int getInitialAmount() {
		return initialAmount;
	}
	public void setInitialAmount(int initialAmount) {
		this.initialAmount = initialAmount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public List<Entry> getEntry() {
		return entry;
	}
	public void setEntry(List<Entry> entry) {
		this.entry = entry;
	}
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void setProduct(Product product) {
		this.price = product.price;
		this.seller = product.seller;
		this.movieId = product.movieId;
		this.title = product.title;
	}
}
