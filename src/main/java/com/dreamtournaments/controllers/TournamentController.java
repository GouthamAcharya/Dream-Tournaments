package com.dreamtournaments.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dreamtournaments.models.Tournament;
import com.dreamtournaments.models.TournamentRegistration;
import com.dreamtournaments.services.ITournamentService;

@RestController
@RequestMapping("/dream-tournaments")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class TournamentController {

	@Autowired
	ITournamentService iTournamentService;

	@PostMapping("/tournament/posts")
	public ResponseEntity<String> postTournament(@RequestBody Tournament tournament) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "posting tournament data");
		iTournamentService.postTournament(tournament);
		String message = "This post is being verified and it will be live soon";
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(message);
	}

	@PostMapping("/tournament/{tournamentId}/register")
	public ResponseEntity<String> registerForTournament(@PathVariable("tournamentId") String tournamentId,
			@RequestBody TournamentRegistration tournamentRegisterData) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "registering for the tournament");
		iTournamentService.registerForTournament(tournamentRegisterData, tournamentId);
		String message = "Registration successfull";
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(message);

	}

	@GetMapping("/tournament/posts/all")
	public ResponseEntity<List<Tournament>> getAllTournaments() {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "fetching all tournaments data");
		List<Tournament> mTournaments = iTournamentService.getAllTournaments();
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(mTournaments);
	}

	@GetMapping("/tournament/posts/status/{post-status}")
	public ResponseEntity<List<Tournament>> getTournamentsByPostStatus(@PathVariable("post-status") String postStatus) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "tournaments data for given status");
		List<Tournament> mTournaments = iTournamentService.getTournamentsByPostStaus(postStatus);
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(mTournaments);
	}

	@GetMapping("/tournament/posts/{tournamentId}")
	public ResponseEntity<Tournament> getTournamentById(@PathVariable("tournamentId") String tournamentId) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "fetching all tournaments data");
		Tournament mTournament = iTournamentService.getTournamentById(tournamentId);
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(mTournament);
	}

	@GetMapping("/tournament/search/posts")
	public ResponseEntity<List<Tournament>> getTournamentByRegex(@RequestParam("searchString") String searchString) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "tournaments data for given input");
		List<Tournament> mTournaments = iTournamentService.getTournamentByTournamentRegex(searchString);
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(mTournaments);
	}

	@GetMapping("/tournament/{tournamentId}/registrations")
	public ResponseEntity<List<TournamentRegistration>> getTournamentRegistrations(
			@PathVariable("tournamentId") String tournamentId) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "tournament registrations data");
		List<TournamentRegistration> mRegistrations = iTournamentService.getTournamentsRegistrations(tournamentId);
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(mRegistrations);

	}

}
