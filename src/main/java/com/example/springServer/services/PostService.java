package com.example.springServer.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springServer.models.*;
import com.example.springServer.repositories.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "true")
public class PostService {
	@Autowired
	PostRepository postRepo;
	@Autowired
	CriticRepository criticRepo;
	
	@PostMapping("/api/post/{userId}")
	public Post createPost(@RequestBody Post post, 
			@PathVariable("userId")int userId,
			HttpSession session) {
//		Critic currentUser = (Critic)session.getAttribute("user");
//		if (currentUser.getRole() != "reviewer") return null;
		Optional<Critic> data = criticRepo.findById(userId);
		if (data.isPresent()) {
			post.setCritic(data.get());
			return postRepo.save(post);	
		}
		return null;	
	}
//	@PostMapping("/api/post")
//	public Post createPost(@RequestBody Post post, HttpSession session) {
//		Person currentUser = (Person)session.getAttribute("user");
//		if (currentUser.getRole() != "reviewer") return null;

//			post.setCritic((Critic)currentUser);
//			return postRepo.save(post);
//		Critic c = (Critic)currentUser;
//			return 	currentUser;
//		}
//		return null;
//	}
	@GetMapping("/api/post")
	public List<Post> findAllPosts() {
		return (List<Post>)postRepo.findAll();
	}
	@GetMapping("/api/movie/{movieId}/post")
	public List<Post> findPostsForMovie(
			@PathVariable("movieId") String movieId) {
		return (List<Post>)postRepo.findPostsForMovie(movieId);
	}
	@GetMapping("/api/post/{postId}")
	public Post findPostById(
			@PathVariable("postId")int postId) {
		Optional<Post> data = postRepo.findById(postId);
		if (data.isPresent()) {
			Post post = data.get();
			return post;
		}
		return null;
	}
	@PutMapping("api/post/{postId}")
	public Post updatePostById(
			@PathVariable("postId")int postId,
			@RequestBody Post newPost) {
		Optional<Post> data = postRepo.findById(postId);
		if (data.isPresent()) {
			Post post = data.get();
			post.setTitle(newPost.getTitle());
			post.setContent(newPost.getContent());
			post.setScore(newPost.getScore());
			return postRepo.save(post);
		}
		return null;
	}
	@DeleteMapping("api/post/{postId}")
	public void deletePostById(
			@PathVariable("postId")int postId) {
		Optional<Post> data = postRepo.findById(postId);
		if (data.isPresent()) {
			Post post = data.get();
			postRepo.delete(post);
		}	
	}
	

	
}
