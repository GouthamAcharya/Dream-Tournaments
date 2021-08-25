package com.dreamtournaments.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SportsClubRegistration {

	String username;
	String userId;
	Address userLocation;
	String registrationDate;
	String contactNumber;
	String teamName;
	
}
