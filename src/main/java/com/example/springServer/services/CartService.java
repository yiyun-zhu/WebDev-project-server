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
	
	@Autowired
	ProductRepository productRepo;
		
	@GetMapping("api/entries")
	public List<Entry> findAllEntries() {
		return (List<Entry>)entryRepository.findAll();
	}
	
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
	
	@PostMapping("/api/buyer/{bId}/product/{pId}/entry")
	public Entry addEntryToCart(
			@PathVariable("bId")int bId,
			@PathVariable("pId")int pId,
			@RequestBody Entry entry) {
		Optional<Product> data1 = productRepo.findById(pId);
		Optional<Buyer> data2 =  buyerRepository.findById(bId);
		if (data1.isPresent() && data2.isPresent()) {
			Buyer buyer = data2.get();
			entry.setBuyer(buyer);
			Product product = data1.get();
			entry.setProduct(product);
			entry.setName(buyer.getUsername() + "purchased" + product.getTitle());
			return entryRepository.save(entry);
		}
		return null;
	}
	
}
