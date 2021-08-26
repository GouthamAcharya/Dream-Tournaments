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

import com.dreamtournaments.models.SportsClub;
import com.dreamtournaments.models.SportsClubRegistration;
import com.dreamtournaments.services.ISportsClubService;

@RestController
@RequestMapping("/dream-tournaments")
public class SportsClubController {

	@Autowired
	ISportsClubService sportsClubService;

	@PostMapping("/sports-club/posts")
	public ResponseEntity<String> postSportsClub(@RequestBody SportsClub sportsClub) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "posting sports club data");
		sportsClubService.postSportsClub(sportsClub);
		String message = "This post is being verified and it will be live soon";
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(message);
	}

	@PostMapping("/sports-club/{sportsClubId}/register-for-club")
	public ResponseEntity<String> registerForSportsClub(@PathVariable("sportsClubId") String sportsClubId,
			@RequestBody SportsClubRegistration sportsClubRegistration) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "registering for the sports club");
		sportsClubService.registerForSportsClub(sportsClubRegistration, sportsClubId);
		String message = "Registration successfull";
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(message);

	}

	@GetMapping("/sports-club/posts/status/{post-status}")
	public ResponseEntity<List<SportsClub>> getSportsClubByPostStatus(@PathVariable("post-status") String postStatus) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "sports club data for given status");
		List<SportsClub> mSportsClub = sportsClubService.getSportsClubByPostStaus(postStatus);
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(mSportsClub);
	}

	@GetMapping("/sports-club/posts")
	public ResponseEntity<List<SportsClub>> getSportsClubByRegex(@RequestParam("searchString") String searchString) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "sports club data for given input");
		List<SportsClub> mSportsClub = sportsClubService.getSportsClubByRegex(searchString);
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(mSportsClub);
	}

	@GetMapping("/sports-club/{sportsClubId}/registrations")
	public ResponseEntity<List<SportsClubRegistration>> getSportsClubRegistrations(
			@PathVariable("sportsClubId") String sportsClubId) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "sports club registrations data");
		List<SportsClubRegistration> mRegistrations = sportsClubService.getSportsClubRegistrations(sportsClubId);
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(mRegistrations);

	}

}
