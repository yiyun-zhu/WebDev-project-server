package com.example.springServer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.springServer.models.User;

public interface UserRepository 
	extends CrudRepository<User, Integer>{

}
