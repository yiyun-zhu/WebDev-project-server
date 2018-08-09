package com.example.springServer.services;

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

import com.example.springServer.repositories.BuyerRepository;
import com.example.springServer.repositories.EntryRepository;
import com.example.springServer.repositories.OrderRepository;
import com.example.springServer.models.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderService {
	@Autowired 
	EntryRepository entryRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired 
	BuyerRepository buyerRepository;
	
	@GetMapping("/api/entry/{eid}")
	public Entry findEntryByEntryId(@PathVariable("eid") int id) {
		Optional<Entry> data = entryRepository.findById(id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
//	@PostMapping("/api/buyer/{bid}/order")
//	public Orders createOrder(@RequestBody Orders order, @PathVariable("bid") int id) {
//		Optional<Buyer> data = buyerRepository.findById(id);
//		if (data.isPresent()) {
//			Buyer buyer = data.get();
//			order.setBuyer(buyer);
//			return orderRepository.save(order);
//		}
//		return null;
//	}
	
	@PostMapping("/api/buyer/{bid}/order")
	public Orders createOrder(@RequestBody Orders order, @PathVariable("bid") int id) {
//		Orders newOrder = new Orders();
		Optional<Buyer> data = buyerRepository.findById(id);
		if (data.isPresent()) {
			Buyer buyer = data.get();
			order.setBuyer(buyer);
			List<Entry> cartItems = buyer.getCartItems();
			if (cartItems != null) {
				for (Entry item : cartItems) {
					item.setOrder(order);
				}
			}
			order.setEntry(cartItems);
//			buyer.setCartItems(null);
			return orderRepository.save(order);
		}
		return null;
	}	
	
	@PutMapping("/api/order/{oid}")
	public Orders updateOrder(@RequestBody Orders order, @PathVariable("oid") int id) {
		Optional<Orders> data = orderRepository.findById(id);
		if (data.isPresent()) {
			Orders oldOrder = data.get();
			oldOrder.setOrder(order);
			orderRepository.save(oldOrder);
			return oldOrder;
		}
		return null;
	}
	
	@DeleteMapping("/api/order/{oid}")
	public void deleteOrder(@PathVariable("oid") int id) {
		orderRepository.deleteById(id);
	}
	
}
