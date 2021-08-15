package com.dreamtournaments.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dreamtournaments.models.Tournament;
import com.dreamtournaments.services.ITournamentService;

@RestController
@RequestMapping("/dream-tournaments")
public class TournamentController {

	@Autowired
	ITournamentService tournamentService;

	@PostMapping("/tournament/posts")
	public ResponseEntity<String> postTournament(@RequestBody Tournament tournament) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "posting tournament data");
		tournamentService.postTournament(tournament);
		String message = "This post is being verified and it will be live soon";
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(message);

	}

	@GetMapping("/tournament/posts/status/{post-status}")
	public ResponseEntity<List<Tournament>> getTournamentsByPostStatus(@PathVariable("post-status") String postStatus) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "tournaments data for given status");
		List<Tournament> mTournamnets = tournamentService.getTournamentsByPostStaus(postStatus);
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(mTournamnets);
	}

	@GetMapping("/tournament/posts")
	public ResponseEntity<List<Tournament>> getTournamentsByTitle(@RequestParam("title") String title) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "tournaments data for given title");
		List<Tournament> mTournamnets = tournamentService.getTournamentsByTitle(title);
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(mTournamnets);
	}

}
