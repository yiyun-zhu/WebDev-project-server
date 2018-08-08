package com.example.springServer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.springServer.models.Product;

public interface ProductRepository  extends CrudRepository<Product, Integer> {

}
