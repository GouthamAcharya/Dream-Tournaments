package com.dreamtournaments.exception;

public class TournamentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TournamentNotFoundException(String message) {

		super(message);
	}

}
