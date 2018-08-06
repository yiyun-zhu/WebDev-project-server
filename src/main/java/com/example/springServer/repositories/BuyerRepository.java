package com.example.springServer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.springServer.models.Buyer;

public interface BuyerRepository 
		extends CrudRepository<Buyer, Integer>{

}
