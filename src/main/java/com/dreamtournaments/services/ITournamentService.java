package com.dreamtournaments.services;

import java.util.List;

import com.dreamtournaments.models.Tournament;
import com.dreamtournaments.models.TournamentRegistration;


public interface ITournamentService {

	void postTournament(Tournament tournament);
	
	void deleteTournament(String tournamentId);
	
	void updateTournament(Tournament tournament);
	
	void registerForTournament(TournamentRegistration tournamentRegistration, String tournamentId);
	
	void updateTournamentPostStatus(String tournamentId, String postStatus);
	
	Tournament getTournamentById(String tournamentId);
	
	List<Tournament> getTournamentsByPostStaus(String postStaus);
	
	List<Tournament> getTournamentByTournamentRegex(String searchString);
	
	List<TournamentRegistration> getTournamentsRegistrations(String tournamentId);
	
	List<Tournament> getAllTournaments();
}
