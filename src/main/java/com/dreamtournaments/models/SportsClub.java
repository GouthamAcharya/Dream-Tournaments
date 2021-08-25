package com.dreamtournaments.models;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Document(collection = "sportsClubs")
@NoArgsConstructor
@Data
public class SportsClub {

	@Id
	String _id;
	String clubImgPath;
	String clubName;
	List<String> clubRules;
	String clubDescription;
	Address clubAddress;
	List<String> contactDetails;
	String postStatus;
	List<SportsClubRegistration> registrations;

}
