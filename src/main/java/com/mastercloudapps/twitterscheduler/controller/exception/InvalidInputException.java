package com.mastercloudapps.twitterscheduler.controller.exception;

public class InvalidInputException extends RuntimeException {

	private static final long serialVersionUID = -5050745879025696385L;

	public InvalidInputException(String message) {

		super(message);
	}

	public InvalidInputException(final Exception e) {

		super(e);
	}

	public InvalidInputException(String message, Throwable cause) {

		super(message, cause);
	}
}
