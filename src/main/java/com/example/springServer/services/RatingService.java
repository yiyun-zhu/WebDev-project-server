package com.example.springServer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springServer.models.Entry;
import com.example.springServer.models.Rating;
import com.example.springServer.models.User;
import com.example.springServer.repositories.EntryRepository;
import com.example.springServer.repositories.RatingRepository;
import com.example.springServer.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials="true")
public class RatingService {

	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	EntryRepository entryRepository;
	
	@Autowired
	UserRepository userRepository;
		
	@PostMapping("/api/entry/{eid}/rating")
	public Rating createRating(@PathVariable("eid") int entryId, @RequestBody Rating rating) {
//		int fromId = rating.getFromId();
//		int toId = rating.getToId();
		Optional<Entry> data = entryRepository.findById(entryId);
		if (data.isPresent()) {
			Entry entry = data.get();
			rating.setEntry(entry);
			ratingRepository.save(rating);
			return rating;
		}
		return null;
	}
	
	@GetMapping("/api/user/{uid}/receivedratings")
	public List<Rating> getReceivedRating(@PathVariable("uid") int userId) {
		List<Rating> ratings = new ArrayList<>();
		Optional<User> data = userRepository.findById(userId);
		if (data.isPresent()) {
//			User user = data.get();
			List<Rating> ratingRepo = (List<Rating>) ratingRepository.findAll();
			for (Rating rt: ratingRepo) {
				if (rt.getToId() == userId) {
					ratings.add(rt);
				}
			}
			return ratings;
		}
		return null;
	}
	
	@GetMapping("/api/user/{uid}/postedratings")
	public List<Rating> getPostedRating(@PathVariable("uid") int userId) {
		List<Rating> ratings = new ArrayList<>();
		Optional<User> data = userRepository.findById(userId);
		if (data.isPresent()) {
//			User user = data.get();
			List<Rating> ratingRepo = (List<Rating>) ratingRepository.findAll();
			for (Rating rt: ratingRepo) {
				if (rt.getFromId() == userId) {
					ratings.add(rt);
				}
			}
			return ratings;
		}
		return null;
	}
	
	@PutMapping("/api/rating/{rid}")
	public Rating updateRating(@PathVariable("rid") int ratingId,  @RequestBody Rating rating) {
		Optional<Rating> data = ratingRepository.findById(ratingId);
		if (data.isPresent()) {
			Rating oldRating = data.get();
			oldRating.setScore(rating.getScore());
			ratingRepository.save(oldRating);
			return oldRating;
		}
		return null;
	}
	
	@DeleteMapping("/api/rating/{rid}")
	public void deleteRating(@PathVariable("rid") int ratingId) {
		ratingRepository.deleteById(ratingId);
	}
}
