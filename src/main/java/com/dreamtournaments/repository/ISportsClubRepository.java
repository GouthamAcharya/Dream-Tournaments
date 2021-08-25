package com.dreamtournaments.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.dreamtournaments.models.SportsClub;

@Repository
public interface ISportsClubRepository extends MongoRepository<SportsClub, String>{
	
	List<SportsClub> findByPostStatus(String postStatus); 
	
	List<SportsClub> findByClubName(String clubName);
	
	@Query("{'clubName': {'$regex': '?0', '$options': 'i'}}")
	List<SportsClub> findBySportsClubByRegex(String searchString);
	
}
