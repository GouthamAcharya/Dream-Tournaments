package com.dreamtournaments.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.dreamtournaments.models.SportsClub;
import com.dreamtournaments.models.Tournament;

@Repository
public interface ISportsClubRepository extends MongoRepository<SportsClub, String>{

	List<Tournament> findByPostStatus(String postStatus); 
	
	
	
}
