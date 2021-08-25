package com.dreamtournaments.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dreamtournaments.models.SportsClub;
import com.dreamtournaments.models.SportsClubRegistration;

public interface ISportsClubService {

	void postSportsClub(SportsClub sportsClub);
	
	void deleteSportsClub(String sportsClubId);
	
	void updateSportsClub(SportsClub sportsClub);
	
	void registerForSportsClub(SportsClubRegistration sportsClubRegistration, String sportsClubId);
	
	void updateSportsClubStatus(String sportsClubId, String postStatus);
	
	List<SportsClub> getSportsClubByPostStaus(String postStaus);
	
	List<SportsClub> getSportsClubByRegex(String searchString);
	
	List<SportsClubRegistration> getSportsClubRegistrations(String sportsClubId);
	
}
