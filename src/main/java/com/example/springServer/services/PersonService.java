package com.example.springServer.services;

import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springServer.models.*;
import com.example.springServer.repositories.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "true")
public class PersonService {
	@Autowired
	PersonRepository repository;
	@Autowired
	UserRepository userRepo;
	@Autowired
	CriticRepository criticRepo;
	@Autowired
	AdminRepository adminRepo;
	@Autowired
	BuyerRepository buyerRepo;
	@Autowired
	SellerRepository sellerRepo;
	
	// register
	@PostMapping("/api/register")
	public Person register(@RequestBody Person newUser, HttpSession session) { //
		Iterable<Person> person = repository.
				findPersonByUsername(newUser.getUsername());
		Iterator<Person> itr = person.iterator();
		if (itr.hasNext()) {
			return null;
		}
		switch(newUser.getRole()) {
//			case "admin":
//				adminRepo.save((Admin)newUser);
			case "reviewer":
				criticRepo.save((Critic)newUser);
			case "buyer":
				buyerRepo.save((Buyer)newUser);
			case "seller":
				sellerRepo.save((Seller)newUser);
			case "":
				userRepo.save((User)newUser);
		}
		session.setAttribute("user", newUser);
		return newUser;
	}
	
	// register
	@PostMapping("/api/register/reviwer")
	public Critic register(@RequestBody Critic newUser, HttpSession session) { //
		Iterable<Person> person = repository.
				findPersonByUsername(newUser.getUsername());
		Iterator<Person> itr = person.iterator();
		if (itr.hasNext()) {
			return null;
		}
		criticRepo.save(newUser);
		session.setAttribute("user", newUser);
		return newUser;
	}
	
	// findAll
	@GetMapping("/api/person")
	public Iterable<Person> findAllPersons(
			@RequestParam(name="username",
			required=false) String username,
			@RequestParam(name="password",
			required=false) String password) {
		if (username != null && password != null) {
			Iterable<Person> user = repository.
					findPersonByCredentials(username, password);
			return user;  
		} else if (username != null) {
			Iterable<Person> user = repository.findPersonByUsername(username);
			return user;
		} else {
			return repository.findAll();
		}
	}
	
	// login
	@PostMapping("/api/login")
	public Person login(@RequestBody Person user, HttpSession session) { //
		Iterable<Person> registedUser = findAllPersons(
				user.getUsername(), user.getPassword());
		Iterator<Person> itr = registedUser.iterator();
		if (itr.hasNext()) {
			Person loggedinUser = itr.next();
			session.setAttribute("user", loggedinUser);
			return (Person)session.getAttribute("user");
		}
		return null;
	}

	// get profile
	@GetMapping("api/profile")
	public Person getProfile(HttpSession session) {
		Person currentUser = (Person)session.getAttribute("user");
		return (currentUser != null)? currentUser : null;		
	}
	
	// updateProfile
	@PutMapping("api/profile")
	public Person updateProfile(@RequestBody Person newUser, HttpSession session) {
		Person currentUser = (Person)session.getAttribute("user");
		if (currentUser != null) {
			currentUser.setAvatar(newUser.getAvatar());
			currentUser.setEmail(newUser.getEmail());
			repository.save(currentUser);
			return currentUser;
		}
		return null;
	}
	
	//logout
	@PostMapping("api/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}
}
