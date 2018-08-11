package com.example.springServer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.springServer.models.Rating;

public interface RatingRepository 
		extends CrudRepository<Rating, Integer>{

}
