package com.mastercloudapps.twitterscheduler.domain.exception;

import com.mastercloudapps.twitterscheduler.domain.shared.Message;

public class MessageMaxLengthExceededException extends RuntimeException {
	
	private static final long serialVersionUID = 5524573919396847821L;
	
	public MessageMaxLengthExceededException(String message) {

		super("Message exceeds Twitter allowed max length (" + Message.maxLength() + ")"
		+ ". Message length is " + message.length() 
		+ ". Message: " + message);
	}

}
