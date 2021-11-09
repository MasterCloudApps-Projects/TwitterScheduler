package com.mastercloudapps.twitterscheduler.domain.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -5366872465471318718L;

	public ServiceException(final String message) {
		super(message);
	}

	public ServiceException(final Throwable throwable) {
		super(throwable);
	}

	public ServiceException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
