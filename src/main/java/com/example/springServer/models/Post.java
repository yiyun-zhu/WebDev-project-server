package com.example.springServer.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	private String content;
	private double score;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
	
	private String imbdID;
	public String getImbdID() {
		return imbdID;
	}
	public void setImbdID(String imbdID) {
		this.imbdID = imbdID;
	}
	@ManyToOne
	private Critic critic;
	@OneToMany(mappedBy="post", orphanRemoval =true)
	private List<Comment> comments;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Critic getCritic() {
		return critic;
	}
	public void setCritic(Critic critic) {
		this.critic = critic;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	} 
	
	
	
}
