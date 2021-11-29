package com.mastercloudapps.twitterscheduler.controller.exception;

public class MalformedImageUrlException extends RuntimeException {

	private static final long serialVersionUID = 1607171163255641096L;

	public MalformedImageUrlException(String url) {

		super("Bad url format: '" + url + "'");
	}

}

