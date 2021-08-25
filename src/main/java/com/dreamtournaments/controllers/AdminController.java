package com.dreamtournaments.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dreamtournaments.services.ISportsClubService;
import com.dreamtournaments.services.ITournamentService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/dream-tournaments/admin")
public class AdminController {

	@Autowired
	ITournamentService iTournamentService;

	@Autowired
	ISportsClubService iSportsClubService;

	@PatchMapping("/tournaments/posts/{tournamentId}")
	public ResponseEntity<String> updateTournamentPostStatus(@RequestParam("status") String status,
			@PathVariable("tournamentId") String tournamentId) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "updating tournament post status");
		iTournamentService.updateTournamentPostStatus(tournamentId, status);
		String message = "Post status updated";
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(message);
	}

	@PatchMapping("/sports-club/posts/{sportsClubId}")
	public ResponseEntity<String> updateSportsClubPostStatus(@RequestParam("status") String status,
			@PathVariable("sportsClubId") String sportsClubId) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "updating sports club post status");
		iSportsClubService.updateSportsClubStatus(sportsClubId, status);
		String message = "Post status updated";
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(message);
	}

}
