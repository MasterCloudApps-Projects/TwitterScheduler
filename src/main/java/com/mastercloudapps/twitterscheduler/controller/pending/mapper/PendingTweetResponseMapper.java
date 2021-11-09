package com.mastercloudapps.twitterscheduler.controller.pending.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.controller.exception.InvalidInputException;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingTweetResponse;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweet;

@Component
public class PendingTweetResponseMapper {

	public PendingTweetResponse mapResponse(PendingTweet pendingTweet) {
		
		if (Optional.ofNullable(pendingTweet).isEmpty()) {
			throw new InvalidInputException("Invalid payload");
		}
		
		final var id = this.mapId(pendingTweet);
		final var message = this.mapMessage(pendingTweet);
		final var publicationDate = this.mapPublicationDate(pendingTweet);
		final var createdAt = this.mapCreatedAt(pendingTweet);
		
		var responseBuilder = PendingTweetResponse
				.builder()
				.id(id)
				.message(message)
				.publicationDate(publicationDate)
				.createdAt(createdAt);
		
		return responseBuilder.build();
	}
	
	private Long mapId(final PendingTweet request) {

		return request.id().id();
	}
	
	private String mapMessage(final PendingTweet request) {

		return request.message().message();
	}

	private String mapPublicationDate(final PendingTweet request) {

		return request.publicationDate().getFormatted();
	}
	
	private String mapCreatedAt(final PendingTweet request) {

		return request.createdAt().getFormatted();
	}
}
