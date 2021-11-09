package com.mastercloudapps.twitterscheduler.controller.pending.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.application.model.operation.FindOnePendingTweetOperation;
import com.mastercloudapps.twitterscheduler.controller.exception.InvalidInputException;

@Component
public class FindOnePendingTweetRequestMapper {

	public FindOnePendingTweetOperation mapRequest(final Long id) {

		if (Optional.ofNullable(id).isEmpty()) {
			throw new InvalidInputException("Invalid payload");
		}
		
		return FindOnePendingTweetOperation
				.builder()
				.id(id)
				.build();
	}
}
