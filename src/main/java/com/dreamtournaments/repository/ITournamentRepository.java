package com.dreamtournaments.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.dreamtournaments.models.Tournament;

@Repository
public interface ITournamentRepository extends MongoRepository<Tournament, String>{
	
	List<Tournament> findByPostStatus(String postStatus); 
	
	List<Tournament> findByTitle(String title);
	
	@Query("{'title': {'$regex': '?0', '$options': 'i'}}")
	List<Tournament> findByTournamentByRegex(String searchString);
	
}
