package com.mastercloudapps.twitterscheduler.application.service;

import static java.util.function.Function.identity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.application.model.operation.PublishPendingTweetOnDemandOperation;
import com.mastercloudapps.twitterscheduler.application.model.scheduler.SchedulerConfiguration;
import com.mastercloudapps.twitterscheduler.application.model.twitter.PublishTweetRequest;
import com.mastercloudapps.twitterscheduler.application.model.twitter.PublishTweetResponse;
import com.mastercloudapps.twitterscheduler.application.service.twitter.TwitterService;
import com.mastercloudapps.twitterscheduler.application.usecase.PublishPendingTweetOnDemandUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.PublishPendingTweetsUseCase;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweet;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweetPort;
import com.mastercloudapps.twitterscheduler.domain.shared.NullableInstant;
import com.mastercloudapps.twitterscheduler.domain.tweet.PublicationType;
import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;
import com.mastercloudapps.twitterscheduler.domain.tweet.TweetImage;
import com.mastercloudapps.twitterscheduler.domain.tweet.TweetPort;

@Component
public class PublisherService implements PublishPendingTweetsUseCase, PublishPendingTweetOnDemandUseCase {

	private static Logger logger = LoggerFactory.getLogger(PublisherService.class);

	private SchedulerConfiguration schedulerConfiguration;

	private TwitterService twitterService;

	private PendingTweetPort pendingTweetPort;

	private TweetPort tweetPort;

	public PublisherService(final SchedulerConfiguration schedulerConfiguration, 
			final TwitterService twitterService, final PendingTweetPort pendingTweetPort, 
			final TweetPort tweetPort) {

		this.schedulerConfiguration = schedulerConfiguration;
		this.twitterService = twitterService;
		this.pendingTweetPort = pendingTweetPort;
		this.tweetPort = tweetPort;
	}

	@Override
	public void publishPending() {

		Instant publishUntil = schedulerConfiguration.publishUntil();

		List<PendingTweet> pendingTweets = pendingTweetPort.findPendingForPublish(publishUntil).stream()
				.map(identity())
				.collect(Collectors.toList());

		logger.info("Recovered " + pendingTweets.size() + " pending tweets to publish");

		pendingTweets.forEach(pending -> {
			
			final var publishedTweet = twitterService.publish(this.buildPublishTweetRequest(pending));

			logger.info("Successful scheduled publication for pending tweet with id = " + pending.id().id());
			
			publishedTweet.ifPresent(published -> {

				pendingTweetPort.delete(pending.id().id());
				
				tweetPort.create(this
						.buildTweet(publishedTweet.get(), pending, PublicationType.SCHEDULED));	
				
			});
		});		
	}

	@Override
	public Optional<Tweet> publishImmediatly(PublishPendingTweetOnDemandOperation operation) {

		final var pending = pendingTweetPort.findOne(operation.getId());

		if (pending.isPresent()) {

			logger.info("Found pending tweet with id = " + pending.get().id() + " to publish on demand");
			
			final var publishedTweet = twitterService.publish(this.buildPublishTweetRequest(pending.get()));

			if (publishedTweet.isPresent()) {
				
				logger.info("Successful on demand publication for pending tweet with id = " + pending.get().id().id()
						+ "tweetId is " + publishedTweet.get().getId());
				
				pendingTweetPort.delete(pending.get().id().id());

				final var tweet = tweetPort.create(this
						.buildTweet(publishedTweet.get(), pending.get(), PublicationType.ON_DEMAND));

				return Optional.of(tweet);
			}		
		}
		return Optional.empty();
	}
	
	private PublishTweetRequest buildPublishTweetRequest(PendingTweet pendingTweet) {
		
		final var builder = PublishTweetRequest.builder()
				.message(pendingTweet.message().message());
		
			Optional.ofNullable(pendingTweet.getImages())
			.ifPresent(images -> {
				builder.images(images.stream()
						.map(image -> image.url().url())
						.collect(Collectors.toList()));
			});
		
		return builder.build();
	}
	
	private Tweet buildTweet(PublishTweetResponse publishTweetResponse, PendingTweet pendingTweet, PublicationType publicationType) {
		
		final var builder = Tweet.builder()
				.id(publishTweetResponse.getId())
				.message(publishTweetResponse.getMessage())
				.url(publishTweetResponse.getUrl())
				.requestedPublicationDate(pendingTweet.publicationDate().instant())
				.publishedAt(publishTweetResponse.getPublishedAt())
				.createdAt(NullableInstant.now().instant())
				.publicationType(publicationType);

		if (!publishTweetResponse.getImages().isEmpty()) {
			builder.images(publishTweetResponse.getImages().stream()
					.map(image -> TweetImage.builder()
							.id(image.getId())
							.size(image.getSize())
							.type(image.getType())
							.width(image.getWidth())
							.height(image.getHeight())
							.build())
					.collect(Collectors.toList()));	
		}
		
		return builder.build();		
	}

}