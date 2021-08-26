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
	ITournamentRepository tournamentRepository;

	@Override
	public void postTournament(Tournament tournament) {

		tournamentRepository.insert(tournament);
	}

	@Override
	public void deleteTournament(String tournamentId) {

		if (tournamentRepository.existsById(tournamentId))
			tournamentRepository.deleteById(tournamentId);
		else
			throw new TournamentNotFoundException("No tournament found with this id");

	}

	@Override
	public void updateTournament(Tournament tournament) {

		tournamentRepository.save(tournament);
	}

	@Override
	public void registerForTournament(TournamentRegistration tournamentRegistrationData, String tournamentId) {

		Optional<Tournament> tournament = tournamentRepository.findById(tournamentId);

		if (tournament.isEmpty())
			throw new TournamentNotFoundException("invalid tournament id.");

		if (!(tournament.get().getRegistrations() == null))
			tournament.get().getRegistrations().add(tournamentRegistrationData);
		else {
			List<TournamentRegistration> tournamentRegistrationsList = new ArrayList<>();
			tournamentRegistrationsList.add(tournamentRegistrationData);
			tournament.get().setRegistrations(tournamentRegistrationsList);
		}

		tournamentRepository.save(tournament.get());

	}

	@Override
	public void updateTournamentPostStatus(String tournamentId, String postStatus) {

		Optional<Tournament> tournament = tournamentRepository.findById(tournamentId);

		if (tournament.isEmpty())
			throw new TournamentNotFoundException("Tournament not found.");

		tournament.get().setPostStatus(postStatus);
		tournamentRepository.save(tournament.get());

	}

	@Override
	public List<Tournament> getTournamentsByPostStaus(String postStaus) {

		List<Tournament> tournaments = tournamentRepository.findByPostStatus(postStaus);

		if (tournaments.isEmpty())
			throw new TournamentNotFoundException("No tournments found with status " + postStaus);

		return tournaments;

	}

	@Override
	public List<Tournament> getTournamentByTournamentRegex(String searchString) {

		List<Tournament> tournaments = tournamentRepository.findByTournamentByRegex(searchString);

		if (tournaments.isEmpty())
			throw new TournamentNotFoundException("No tournaments found with this title.");

		return tournaments;
	}

	@Override
	public List<TournamentRegistration> getTournamentsRegistrations(String tournamentId) {

		Optional<Tournament> tournament = tournamentRepository.findById(tournamentId);

		if (tournament.isEmpty())
			throw new TournamentNotFoundException("invalid tournament id.");

		List<TournamentRegistration> tournamentRegistrations = tournament.get().getRegistrations();
		return tournamentRegistrations;
	}

	@Override
	public List<Tournament> getAllTournaments() {
		List<Tournament> tournaments = tournamentRepository.findAll();

		if (tournaments.isEmpty())
			throw new TournamentNotFoundException("No tournaments found");

		return tournaments;
	}

	@Override
	public Tournament getTournamentById(String tournamentId) {
		Optional<Tournament> tournament = tournamentRepository.findById(tournamentId);

		if (tournament.isEmpty())
			throw new TournamentNotFoundException("invalid tournament id.");

		return tournament.get();
	}

}
