package com.mastercloudapps.twitterscheduler.application.service;

import static java.util.function.Function.identity;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.application.model.scheduler.SchedulerConfiguration;
import com.mastercloudapps.twitterscheduler.application.model.twitter.PublishTweetRequest;
import com.mastercloudapps.twitterscheduler.application.service.twitter.TwitterService;
import com.mastercloudapps.twitterscheduler.application.usecase.PublishPendingTweetsUseCase;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweet;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweetPort;
import com.mastercloudapps.twitterscheduler.domain.shared.NullableInstant;
import com.mastercloudapps.twitterscheduler.domain.tweet.PublicationType;
import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;
import com.mastercloudapps.twitterscheduler.domain.tweet.TweetPort;

@Component
public class PublisherService implements PublishPendingTweetsUseCase {

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
			
			logger.info("Published pending tweet with id = " + pending.id().id());
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

}