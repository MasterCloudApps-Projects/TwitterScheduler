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
import com.mastercloudapps.twitterscheduler.application.service.twitter.TwitterService;
import com.mastercloudapps.twitterscheduler.application.usecase.PublishPendingTweetOnDemandUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.PublishPendingTweetsUseCase;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweet;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweetPort;
import com.mastercloudapps.twitterscheduler.domain.shared.NullableInstant;
import com.mastercloudapps.twitterscheduler.domain.tweet.PublicationType;
import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;
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

			final var publishedTweet = twitterService.publish(PublishTweetRequest.builder()
					.message(pending.message().message())
					.build());

			logger.info("Successful scheduled publication for pending tweet with id = " + pending.id().id());
			publishedTweet.ifPresent(published -> {

				pendingTweetPort.delete(pending.id().id());

				tweetPort.create(Tweet.builder()
						.id(published.getId())
						.message(published.getMessage())
						.url(published.getUrl())
						.requestedPublicationDate(pending.publicationDate().instant())
						.publishedAt(published.getPublishedAt())
						.createdAt(NullableInstant.now().instant())
						.publicationType(PublicationType.SCHEDULED)
						.build());				
			});
		});		
	}

	@Override
	public Optional<Tweet> publishImmediatly(PublishPendingTweetOnDemandOperation operation) {

		final var pending = pendingTweetPort.findOne(operation.getId());

		if (pending.isPresent()) {

			logger.info("Found pending tweet with id = " + pending.get().id() + " to publish on demand");
			final var publishedTweet = twitterService.publish(PublishTweetRequest.builder()
					.message(pending.get().message().message())
					.build());

			logger.info("Successful on demand publication for pending tweet with id = " + pending.get().id().id());
			if (publishedTweet.isPresent()) {

				pendingTweetPort.delete(pending.get().id().id());

				final var tweet = tweetPort.create(Tweet.builder()
						.id(publishedTweet.get().getId())
						.message(publishedTweet.get().getMessage())
						.url(publishedTweet.get().getUrl())
						.requestedPublicationDate(pending.get().publicationDate().instant())
						.publishedAt(publishedTweet.get().getPublishedAt())
						.createdAt(NullableInstant.now().instant())
						.publicationType(PublicationType.ON_DEMAND)
						.build());

				return Optional.of(tweet);
			}		
		}
		return Optional.empty();
	}

}