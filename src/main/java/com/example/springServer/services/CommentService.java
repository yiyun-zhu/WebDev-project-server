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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springServer.models.*;
import com.example.springServer.repositories.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "true")
public class CommentService {
	@Autowired
	CommentRepository commentRepo;
	@Autowired
	PostRepository postRepo;
	
	@PostMapping("/api/post/{postId}/comment")
	public Comment createComment(
			@RequestBody Comment comment, 
			@PathVariable("postId")int postId,
			HttpSession session) {
		Person currentUser = (Person)session.getAttribute("user");
		comment.setUser((User)currentUser);
		Optional<Post> data = postRepo.findById(postId);
		if (data.isPresent()) {
			comment.setPost(data.get());
			return commentRepo.save(comment);
		}
		return null;
	}
	
	@GetMapping("/api/post/{postId}/comments")
	public List<Comment> findCommentsForPost(
			@PathVariable("postId") int postId) {
		Optional<Post> data = postRepo.findById(postId);
		if (data.isPresent()) {
			Post post = data.get(); 
			return post.getComments();
		}
		return null;
	}
	
	@GetMapping("/api/comment/{commentId}")
	public Comment findCommentById(
			@PathVariable("commentId")int commentId) {
		Optional<Comment> data = commentRepo.findById(commentId);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@DeleteMapping("/api/comment/{commentId}")
	public void deleteCommentById(
			@PathVariable("commentId")int commentId) {
		commentRepo.deleteById(commentId);
	}

}
