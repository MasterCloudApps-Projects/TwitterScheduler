package com.mastercloudapps.twitterscheduler.controller.tweet.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.application.model.operation.FindOneTweetOperation;
import com.mastercloudapps.twitterscheduler.controller.exception.InvalidInputException;

@Component
public class FindOneTweetRequestMapper {

	public FindOneTweetOperation mapRequest(final Long id) {

		if (Optional.ofNullable(id).isEmpty()) {
			throw new InvalidInputException("Invalid payload");
		}
		
		return FindOneTweetOperation
				.builder()
				.id(id)
				.build();
	}
}
