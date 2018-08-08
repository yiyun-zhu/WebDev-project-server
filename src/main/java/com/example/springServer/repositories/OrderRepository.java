package com.example.springServer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.springServer.models.Order;


public interface OrderRepository extends CrudRepository<Order, Integer> {
	
}
