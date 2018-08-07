package com.example.springServer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.springServer.models.Person;

public interface PersonRepository 
	extends CrudRepository<Person, Integer>{
	@Query("SELECT u FROM Person u WHERE u.username=:username")
	Iterable<Person> findPersonByUsername(@Param("username") String u);
	
	@Query("SELECT u FROM Person u WHERE u.username=:username AND u.password=:password")
	Iterable<Person> findPersonByCredentials(
			@Param("username") String username, 
			@Param("password") String password);

}
