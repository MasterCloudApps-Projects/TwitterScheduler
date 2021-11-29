package com.mastercloudapps.twitterscheduler.controller.exception;

public class ImageNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1607171163255641096L;

	public ImageNotAvailableException(String url) {

		super("Image not available in '" + url + "'");
	}

}