package com.dreamtournaments.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

	@Id
	String _id;
	String username;
	@Indexed
	String email;
	long ContactNumber;
	
}
