package com.mastercloudapps.twitterscheduler.controller.pending.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.controller.exception.InvalidInputException;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PublishOnDemandResponse;
import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;

@Component
public class PublishOnDemandResponseMapper {

	public PublishOnDemandResponse mapResponse(Tweet tweet) {

		if (Optional.ofNullable(tweet).isEmpty()) {
			throw new InvalidInputException("Invalid payload");
		}

		final var id = this.mapId(tweet);

		var responseBuilder = PublishOnDemandResponse
				.builder()
				.tweetId(id);

		return responseBuilder.build();
	}

	private Long mapId(final Tweet request) {

		return request.id().id();
	}
}