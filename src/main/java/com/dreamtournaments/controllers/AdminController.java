package com.dreamtournaments.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dreamtournaments.services.ITournamentService;

@RestController
@RequestMapping("/dream-tournaments/admin")
public class AdminController {

	@Autowired
	ITournamentService tournamentService;

	@PatchMapping("/tournaments/posts")
	public ResponseEntity<String> updatePostStatus(@RequestParam("status") String status, @RequestParam("tournamentId") String tournamentId) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "posting tournament data");
		tournamentService.updatePostStatus(tournamentId, status);
		String message = "Post status updated";
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(message);
	}

}
