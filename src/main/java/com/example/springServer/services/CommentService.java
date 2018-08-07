package com.example.springServer.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	
	@PostMapping("/api/comment")
	public Comment createComment(@RequestBody Comment comment, HttpSession session) {
		Person currentUser = (Person)session.getAttribute("user");
//		if (currentUser.getRole() != "reviewer") return null;
		comment.setUser((User)currentUser);
		return commentRepo.save(comment);
	}
	@GetMapping("/api/post/{postId}/comment")
	public List<Comment> findCommentsForPost(
			@PathVariable("postId") int postId) {
		Optional<Post> data = postRepo.findById(postId);
		if (data.isPresent()) {
			Post post = data.get(); 
			return post.getComments();
		}
		return null;
	}

}
