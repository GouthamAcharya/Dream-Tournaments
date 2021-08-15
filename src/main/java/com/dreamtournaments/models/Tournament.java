package com.dreamtournaments.models;

import java.util.List;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tournament {

	@Id
	String _id;
	String userEmail;
	String imgPath;
	String title;
	String organizerName;
	String startDate;
	String endDate;
	String level;
	String category;
	String type;
	String entryFee;
	Address venue;
	List<String> rules;
	List<String> contactDetails;
	List<String> description;
	String registrationLastDate;
	String postStatus;
	List<TournamentRegistration> registrations;

}
