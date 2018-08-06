package com.example.springServer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.springServer.models.Admin;

public interface AdminRepository 
		extends CrudRepository<Admin, Integer>{

}
