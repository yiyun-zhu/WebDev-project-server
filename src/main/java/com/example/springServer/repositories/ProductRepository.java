package com.example.springServer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.springServer.models.Post;
import com.example.springServer.models.Product;

public interface ProductRepository  
		extends CrudRepository<Product, Integer> {
	@Query("SELECT p FROM Product p WHERE p.movieId=:movieId")
	Iterable<Product> findProductsForMovie(@Param("movieId") String movieId);
	

}
