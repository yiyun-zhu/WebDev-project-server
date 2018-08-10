package com.example.springServer.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Entry {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Product product;
	@ManyToOne
	private Orders order;
	private int amount;
	@ManyToOne
	private Buyer buyer;
	private String name;
//	@OneToMany(mappedBy="entry", orphanRemoval=true)
//	@JsonIgnore
//	private List<Rating> ratings;
	private int sellerScore = -1;;
	
	private int buyerScore = -1; 
	
	public int getSellerScore() {
		return sellerScore;
	}
	public void setSellerScore(int sellerScore) {
		this.sellerScore = sellerScore;
	}
	public int getBuyerScore() {
		return buyerScore;
	}
	public void setBuyerScore(int buyerScore) {
		this.buyerScore = buyerScore;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	
}
