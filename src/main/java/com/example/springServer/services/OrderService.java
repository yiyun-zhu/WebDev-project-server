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
import com.example.springServer.repositories.ProductRepository;
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
	
	@Autowired 
	ProductRepository productRepository;
	
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
	
	@GetMapping("/api/buyer/{bId}/orders")
	public List<Orders> findOrdersByBuyer(
			@PathVariable("bId")int bId) {
		Optional<Buyer> data = buyerRepository.findById(bId);
		if (data.isPresent()) {
			Buyer buyer = data.get();
			return (List<Orders>)buyer.getOrder();
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
		Optional<Buyer> data = buyerRepository.findById(id);
		if (data.isPresent()) {
			Buyer buyer = data.get();
			order.setBuyer(buyer);
			order.setComplete(false);
			order.setName(buyer.getUsername() + " created order " + order.getId());
			orderRepository.save(order);
//			List<Entry> entries = order.getEntry();
			List<Entry> cartItems = buyer.getCartItems();
			if (cartItems != null) {
				for (Entry item : cartItems) {
//					Entry entry = new Entry();
//					entry.setAmount(item.getAmount());
//					entry.setBuyer(item.getBuyer());
//					entry.setProduct(item.getProduct());
					item.setBuyer(null);
					item.setOrder(order);	
					entryRepository.save(item);
//					entries.add(item);
//					entryRepository.delete(item);
				}
			}
			return order;
		}
		return null;
	}
	
	@PutMapping("/api/modify/order")
	public Orders confirmOrder(@RequestBody Orders order) {
		Optional<Orders> data = orderRepository.findById(order.getId());
		if (data.isPresent()) {
			Orders orderToUpdate = data.get();
			List<Entry> entries = orderToUpdate.getEntry();
			for (Entry entry : entries) {
				Product product = entry.getProduct();
				product.setAmount(product.getAmount() - entry.getAmount());
				productRepository.save(product);
			}
			orderToUpdate.setComplete(order.isComplete());
			return orderToUpdate;
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
