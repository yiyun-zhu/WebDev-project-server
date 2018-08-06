package com.example.springServer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.springServer.models.Person;

public interface PersonRepository 
		extends CrudRepository<Person, Integer>{

}
