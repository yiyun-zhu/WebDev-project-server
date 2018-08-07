package com.example.springServer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.springServer.models.Post;

public interface PostRepository 
	extends CrudRepository<Post, Integer>{
	@Query("SELECT p FROM Post p WHERE p.imdbID=:imdbID")
	Iterable<Post> findPostsForMovie(@Param("imdbID") String movieId);
	
}
