package com.mastercloudapps.twitterscheduler.controller.pending.mapper;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.controller.exception.InvalidInputException;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingImageResponse;
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
		
		var builder = PendingTweetResponse
				.builder()
				.id(id)
				.message(message)
				.publicationDate(publicationDate)
				.createdAt(createdAt);
		
		Optional.ofNullable(pendingTweet.getImages())
			.ifPresent(images -> builder.images(images.stream()
				.map(image -> PendingImageResponse.builder()
						.id(image.id().id())
						.url(image.url().url())
						.build())
				.collect(Collectors.toList())));	
		
		return builder.build();
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