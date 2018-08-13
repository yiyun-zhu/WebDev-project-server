package com.example.springServer.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
public class EntryService {
	@Autowired 
	BuyerRepository buyerRepository;
	
	@Autowired 
	SellerRepository sellerRepository;
	
	@Autowired 
	EntryRepository entryRepository;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	OrderRepository orderRepository;
	
	@GetMapping("api/entries")
	public List<Entry> findAllEntries() {
		return (List<Entry>)entryRepository.findAll();
	}
	
	@GetMapping("/api/entry/{eid}")
	public Entry findEntryByEntryId(@PathVariable("eid") int id) {
		Optional<Entry> data = entryRepository.findById(id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	@GetMapping("/api/order/{oId}/entries")
	public List<Entry> findEntriesByOrder(
			@PathVariable("oId")int oId) {
		Optional<Orders> data = orderRepository.findById(oId);
		if (data.isPresent()) {
			Orders order = data.get();
			return (List<Entry>)order.getEntry();
		}
		return null;
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
			entry.setName(buyer.getUsername() + " purchased " + product.getTitle());
			return entryRepository.save(entry);
		}
		return null;
	}
	
	@PutMapping("/api/entry/{entryId}/rate")
	public Entry updateEntryInRate(
			@PathVariable("entryId")int id,
			@RequestBody Entry newEntry) {
			Optional<Entry> data = entryRepository.findById(id);
			if (data.isPresent()) {
				Entry entry = data.get();
				if (newEntry.getBuyerScore() == -1) {
					entry.setSellerScore(newEntry.getSellerScore());					
				}
				if (newEntry.getSellerScore() == -1) {
					entry.setBuyerScore(newEntry.getBuyerScore());					
				}
				return entryRepository.save(entry);
			}
			return null;
	}
	
	@DeleteMapping("/api/entry/{entryId}")
	public void deleteEntryById(
			@PathVariable("entryId")int entryId) {
		entryRepository.deleteById(entryId);
	}
	
	@GetMapping("/api/seller/{sId}/entries")
	 public List<Entry> findEntriesBySeller(
	   @PathVariable("sId")int sId) {
	  List<Entry> result = new LinkedList<>();
	  Optional<Seller> data = sellerRepository.findById(sId);
	  if(data.isPresent()) {
	   Seller seller= data.get();
	   List<Product> products = seller.getProduct();
	   for (Product p : products) {
	    List<Entry> entries = p.getEntry();
	    for (Entry entry : entries) {
	     if (entry.getOrder().isComplete()) {
	      result.add(entry);
	     }
	    }
	   }
	   return result;
	  }
	  return null;
	 }
	
	
}
