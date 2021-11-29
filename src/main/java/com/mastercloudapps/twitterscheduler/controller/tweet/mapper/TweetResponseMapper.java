package com.mastercloudapps.twitterscheduler.controller.tweet.mapper;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;

import com.mastercloudapps.twitterscheduler.configuration.featureflags.Features;
import com.mastercloudapps.twitterscheduler.controller.exception.InvalidInputException;
import com.mastercloudapps.twitterscheduler.controller.tweet.dto.TweetImageResponse;
import com.mastercloudapps.twitterscheduler.controller.tweet.dto.TweetResponse;
import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;

@Component
public class TweetResponseMapper {

	private FeatureManager featureManager;
	
	@Autowired
	public TweetResponseMapper(final FeatureManager featureManager) {
		this.featureManager = featureManager;
	}
	
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
		final var publicationType = this.mapPublicationType(tweet);
		
		var builder = TweetResponse
				.builder()
				.id(id)
				.message(message)
				.url(url)
				.requestedPublicationDate(requestedPublicationDate)
				.publishedAt(publishedAt)
				.createdAt(createdAt)
				.publicationType(publicationType);
		
		if(featureManager.isActive(Features.TWEETS_WITH_IMAGES)) {
			Optional.ofNullable(tweet.getImages())
			.ifPresent(images -> builder.images(images.stream()
				.map(image -> TweetImageResponse.builder()
						.id(image.id().id())
						.size(image.size().size())
						.type(image.type().type())
						.width(image.width().width())
						.height(image.height().height())
						.build())
				.collect(Collectors.toList())));	
		}
		
		return builder.build();
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
	
	private String mapPublicationType(final Tweet request) {
		
		return request.publicationType().name();
	}
}