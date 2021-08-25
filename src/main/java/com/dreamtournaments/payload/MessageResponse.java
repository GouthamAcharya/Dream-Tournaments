package com.dreamtournaments.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageResponse {
	private String message;

	public MessageResponse(String message) {
		super();
		this.message = message;
	}

}
