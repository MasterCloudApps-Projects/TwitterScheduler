package com.mastercloudapps.twitterscheduler.domain.exception;

import com.mastercloudapps.twitterscheduler.domain.shared.NullableInstant;

public class ExpiredPublicationDateException extends RuntimeException {

	private static final long serialVersionUID = -7499998807210565112L;
	
	public ExpiredPublicationDateException(NullableInstant instant, NullableInstant now) {
		
		super("publication date is expired. "
				+ "Proposed date is '" + instant.getFormatted() + "'" 
				+ " and current time is '" + now.getFormatted() + "'");
	}

}
