package com.dreamtournaments.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamtournaments.exception.TournamentNotFoundException;
import com.dreamtournaments.models.Tournament;
import com.dreamtournaments.models.TournamentRegistration;
import com.dreamtournaments.repository.ITournamentRepository;

@Service
public class TournamentServiceImpl implements ITournamentService {

	@Autowired
	ITournamentRepository iTournamentRepository;

	@Override
	public void postTournament(Tournament tournament) {

		iTournamentRepository.insert(tournament);
	}

	@Override
	public void deleteTournament(String tournamentId) {

		iTournamentRepository.deleteById(tournamentId);

	}

	@Override
	public void updateTournament(Tournament tournament) {

		iTournamentRepository.save(tournament);
	}

	@Override
	public void registerForTournament(TournamentRegistration tournamentRegistrationData, String tournamentId) {

		Optional<Tournament> tournament = iTournamentRepository.findById(tournamentId);

		if (tournament.isEmpty())
			throw new TournamentNotFoundException("invalid tournament id.");

		if (!(tournament.get().getRegistrations() == null))
			tournament.get().getRegistrations().add(tournamentRegistrationData);
		else {
			List<TournamentRegistration> tournamentRegistrationsList = new ArrayList<>();
			tournamentRegistrationsList.add(tournamentRegistrationData);
			tournament.get().setRegistrations(tournamentRegistrationsList);
		}

		iTournamentRepository.save(tournament.get());

	}

	@Override
	public void updatePostStatus(String tournamentId, String postStatus) {

		Optional<Tournament> tournament = iTournamentRepository.findById(tournamentId);

		if (tournament.isEmpty())
			throw new TournamentNotFoundException("Tournament not found.");

		tournament.get().setPostStatus(postStatus);
		iTournamentRepository.save(tournament.get());

	}

	@Override
	public List<Tournament> getTournamentsByPostStaus(String postStaus) {

		List<Tournament> tournaments = iTournamentRepository.findByPostStatus(postStaus);

		if (tournaments.isEmpty())
			throw new TournamentNotFoundException("No tournments found with status " + postStaus);

		return tournaments;

	}

	@Override
	public List<Tournament> getTournamentByTournamentRegex(String searchString) {

		List<Tournament> tournaments = iTournamentRepository.findByTournamentByRegex(searchString);

		if (tournaments.isEmpty())
			throw new TournamentNotFoundException("No tournaments found with this title.");

		return tournaments;
	}

	@Override
	public List<TournamentRegistration> getTournamentsRegistrations(String tournamentId) {

		Optional<Tournament> tournament = iTournamentRepository.findById(tournamentId);

		if (tournament.isEmpty())
			throw new TournamentNotFoundException("invalid tournament id.");

		List<TournamentRegistration> tournamentRegistrations = tournament.get().getRegistrations();
		return tournamentRegistrations;
	}

}
