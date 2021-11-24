package com.mastercloudapps.twitterscheduler.controller.pending.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.application.model.operation.PublishPendingTweetOnDemandOperation;
import com.mastercloudapps.twitterscheduler.controller.exception.InvalidInputException;

@Component
public class PublishPendingTweetOnDemandRequestMapper {

	public PublishPendingTweetOnDemandOperation mapRequest(final Long id) {

		if (Optional.ofNullable(id).isEmpty()) {
			throw new InvalidInputException("Invalid payload");
		}

		return PublishPendingTweetOnDemandOperation
				.builder()
				.id(id)
				.build();
	}
}
