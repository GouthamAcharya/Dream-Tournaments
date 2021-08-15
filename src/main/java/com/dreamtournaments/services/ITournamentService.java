package com.dreamtournaments.services;

import java.util.List;

import com.dreamtournaments.models.Tournament;
import com.dreamtournaments.models.TournamentRegistration;


public interface ITournamentService {

	void postTournament(Tournament tournament);
	
	List<Tournament> getTournamentsByTitle(String tournamentTitle);
	
	List<Tournament> getTournamentByAddress(String address);
	
	void deleteTournament(String tournamentId);
	
	void updateTournament(Tournament tournament);
	
	void registerForTournament(TournamentRegistration tournamentRegistration);
	
	void updatePostStatus(String tournamentId, String postStatus);
	
	List<Tournament> getTournamentsByPostStaus(String postStaus);
}
