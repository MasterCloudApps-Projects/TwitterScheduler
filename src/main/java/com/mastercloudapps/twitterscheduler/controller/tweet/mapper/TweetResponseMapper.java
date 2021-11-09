package com.mastercloudapps.twitterscheduler.controller.tweet.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.controller.exception.InvalidInputException;
import com.mastercloudapps.twitterscheduler.controller.tweet.dto.TweetResponse;
import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;

@Component
public class TweetResponseMapper {

	public TweetResponse mapResponse(Tweet tweet) {
		
		if (Optional.ofNullable(tweet).isEmpty()) {
			throw new InvalidInputException("Invalid payload");
		}
		
		final var id = this.mapId(tweet);
		final var message = this.mapMessage(tweet);
		final var url = this.mapUrl(tweet);
		final var requestedPublicationDate = this.mapRequestedPublicationDate(tweet);
		final var publishedAt = this.mapPublishedAt(tweet);
		final var createdAt = this.mapCreatedAt(tweet);
		
		var responseBuilder = TweetResponse
				.builder()
				.id(id)
				.message(message)
				.url(url)
				.requestedPublicationDate(requestedPublicationDate)
				.publishedAt(publishedAt)
				.createdAt(createdAt);
		
		return responseBuilder.build();
	}
	
	
	private Long mapId(final Tweet request) {

		return request.id().id();
	}
	
	private String mapMessage(final Tweet request) {

		return request.message().message();
	}
	
	private String mapUrl(final Tweet request) {

		return request.url().url();
	}

	private String mapRequestedPublicationDate(final Tweet request) {

		return request.requestedPublicationDate().getFormatted();
	}
	
	private String mapPublishedAt(final Tweet request) {

		return request.publishedAt().getFormatted();
	}
	
	private String mapCreatedAt(final Tweet request) {

		return request.createdAt().getFormatted();
	}
}
