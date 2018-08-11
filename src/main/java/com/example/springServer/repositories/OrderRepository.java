package com.example.springServer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.springServer.models.Orders;


public interface OrderRepository extends CrudRepository<Orders, Integer> {
	
}
