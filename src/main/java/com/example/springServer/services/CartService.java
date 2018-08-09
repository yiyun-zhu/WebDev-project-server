package com.example.springServer.services;

import java.util.List;
import java.util.Optional;

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
public class CartService {
	@Autowired 
	BuyerRepository buyerRepository;
	
	@Autowired 
	EntryRepository entryRepository;
	
	@GetMapping("api/buyer/{buyId}/entries")
	public List<Entry> findCartByBuyer(
			@PathVariable("buyId")int buyId) {
		Optional<Buyer> data = buyerRepository.findById(buyId);
		if(data.isPresent()) {
			Buyer buyer= data.get();
			return buyer.getCartItems();
		}
		return null;
	}
	
	@DeleteMapping("/api/entry/{entryId}")
	public void deleteEntryById(
			@PathVariable("entryId")int entryId) {
		entryRepository.deleteById(entryId);
	}
	
	@PostMapping("/api/entry")
	public Entry addEntryToCart(
			@RequestBody Entry entry) {
		return entryRepository.save(entry);
	}
	
}
