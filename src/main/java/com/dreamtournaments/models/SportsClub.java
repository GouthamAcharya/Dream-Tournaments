package com.dreamtournaments.models;

import java.util.List;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SportsClub {

	@Id
	String _id;
	String userId;
	String clubImgPath;
	String clubName;
	List<String> clubRules;
	String clubDescription;
	Address clubAddress;
	List<String> contactDetails;
	String postStatus;

}
