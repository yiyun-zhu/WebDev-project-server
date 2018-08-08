package com.example.springServer.repositories;
import org.springframework.data.repository.CrudRepository;

import com.example.springServer.models.Entry;

public interface EntryRepository
extends CrudRepository<Entry, Integer> {}