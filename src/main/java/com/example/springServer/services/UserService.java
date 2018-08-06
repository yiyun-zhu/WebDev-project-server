package com.example.springServer.services;

import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springServer.models.User;
import com.example.springServer.repositories.UserRepository;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserService {
	@Autowired
	UserRepository repository;
	
	// register
	@PostMapping("/api/register")
	public User register(@RequestBody User newUser, HttpSession session) { //
		Iterable<User> user = repository.findUserByUsername(newUser.getUsername());
		Iterator<User> itr = user.iterator();
		if (itr.hasNext()) {
			return null;
		}
		User newData = repository.save(newUser);;		
		session.setAttribute("user", newData);
		return newData;
	}

}
