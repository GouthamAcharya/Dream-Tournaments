package com.dreamtournaments.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TournamentRegistration {
 
	String username;
	String userId;
	Address userLocation;
	String registrationDate;
	String contactNumber;
	String teamName;
}
