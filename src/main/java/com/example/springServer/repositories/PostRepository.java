package com.example.springServer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.springServer.models.Post;

public interface PostRepository 
	extends CrudRepository<Post, Integer>{

}
