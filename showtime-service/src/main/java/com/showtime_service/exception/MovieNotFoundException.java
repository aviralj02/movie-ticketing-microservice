package com.showtime_service.exception;

public class MovieNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MovieNotFoundException(String message) {
        super(message);
    }
}