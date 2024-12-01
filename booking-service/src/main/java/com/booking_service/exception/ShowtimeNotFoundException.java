package com.booking_service.exception;

public class ShowtimeNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ShowtimeNotFoundException(String message) {
        super(message);
    }
}
