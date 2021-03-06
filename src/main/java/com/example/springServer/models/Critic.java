package com.example.springServer.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Critic extends User {
	private String company;
	@OneToMany(mappedBy="critic", orphanRemoval =true)
	@JsonIgnore
	private List<Post> post;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<Post> getPost() {
		return post;
	}

	public void setPost(List<Post> post) {
		this.post = post;
	}
}
