package com.example.springServer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.springServer.models.Comment;

public interface CommentRepository 
		extends CrudRepository<Comment, Integer> {

}
