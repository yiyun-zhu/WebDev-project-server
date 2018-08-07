package com.example.springServer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.springServer.models.Seller;

public interface SellerRepository 
		extends CrudRepository<Seller, Integer>{

}
