package com.mastercloudapps.twitterscheduler.infrastructure.postgre.tweet;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.domain.exception.RepositoryException;
import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;

@Component
public class TweetJpaMapper {
	
	public Tweet mapEntity(final TweetJpaEntity tweetEntity) {
		
		if (Optional.ofNullable(tweetEntity).isEmpty()) {
			throw new RepositoryException("Empty entity");
		}
		
		return Tweet
				.builder()
				.id(tweetEntity.getId())
				.message(tweetEntity.getMessage())
				.url(tweetEntity.getUrl())
				.requestedPublicationDate(tweetEntity.getRequestedPublicationDate())
				.publishedAt(tweetEntity.getPublishedAt())
				.createdAt(tweetEntity.getCreatedAt())
				.build();
	}
	
	public TweetJpaEntity mapDomainObject(final Tweet tweet) {
		
		if (Optional.ofNullable(tweet).isEmpty()) {
			throw new RepositoryException("Empty domain object");
		}
		
		return TweetJpaEntity
				.builder()
				.id(tweet.id().id())
				.message(tweet.message().message())
				.url(tweet.url().url())
				.requestedPublicationDate(tweet.requestedPublicationDate().instant())
				.publishedAt(tweet.publishedAt().instant())
				.createdAt(tweet.createdAt().instant())
				.build();
	}

}
