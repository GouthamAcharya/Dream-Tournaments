package com.dreamtournaments.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.dreamtournaments.models.SportsClub;

@Repository
public interface ISportsClubRepository extends MongoRepository<SportsClub, String>{

	
}
