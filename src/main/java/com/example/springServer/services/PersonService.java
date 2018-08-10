package com.example.springServer.services;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	// register reviewer
	@PostMapping("/api/register/reviwer")
	public Critic registerReviewer(@RequestBody Critic newUser, HttpSession session) { //
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
	// register buyer
	@PostMapping("/api/register/buyer")
	public Buyer registerBuyer(@RequestBody Buyer newUser, HttpSession session) { //
		Iterable<Person> person = repository.
				findPersonByUsername(newUser.getUsername());
		Iterator<Person> itr = person.iterator();
		if (itr.hasNext()) {
			return null;
		}
		buyerRepo.save(newUser);
		session.setAttribute("user", newUser);
		return newUser;
	}	
	// register seller
	@PostMapping("/api/register/seller")
	public Seller registerSeller(@RequestBody Seller newUser, HttpSession session) { //
		Iterable<Person> person = repository.
				findPersonByUsername(newUser.getUsername());
		Iterator<Person> itr = person.iterator();
		if (itr.hasNext()) {
			return null;
		}
		sellerRepo.save(newUser);
		session.setAttribute("user", newUser);
		return newUser;
	}
	// register user
	@PostMapping("/api/register/user")
	public User registerUser(@RequestBody User newUser, HttpSession session) { //
		Iterable<Person> person = repository.
				findPersonByUsername(newUser.getUsername());
		Iterator<Person> itr = person.iterator();
		if (itr.hasNext()) {
			return null;
		}
		userRepo.save(newUser);
		session.setAttribute("user", newUser);
		return newUser;
	}
	// find by username, by credentials, find all in one function 
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
	// find by id
	@GetMapping("/api/person/{pId}")
	public Person findPersonById(@PathVariable("pId")int pId) {
		Optional<Person> data = repository.findById(pId);
		if (data.isPresent()) {
			Person p = data.get();
			return p;
		}
		return null;
	}
	// update a person
	@PutMapping("/api/person/{pId}")
	public Person updatePerson(
			@PathVariable("pId")int pId,
			@RequestBody Person newPerson) {
		Optional<Person> data = repository.findById(pId);
		if (data.isPresent()) {
			Person p = data.get();
			p.setAvatar(newPerson.getAvatar());
			p.setEmail(newPerson.getEmail());
			p.setPassword(newPerson.getPassword());
			p.setRole(newPerson.getRole());
			return repository.save(p);
		}
		return null;
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
		Person currentUser = (Person)session.getAttribute("user");
		if (currentUser != null) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			currentUser.setLastLogin(timestamp);
			repository.save(currentUser);
			session.invalidate();
		}
	}
}
