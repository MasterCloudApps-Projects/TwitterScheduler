package com.mastercloudapps.twitterscheduler.infrastructure.jpa.tweet;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.domain.exception.RepositoryException;
import com.mastercloudapps.twitterscheduler.domain.tweet.TweetImage;

@Component
public class TweetImageJpaMapper {

	public TweetImage mapEntity(final TweetImageJpaEntity tweetImageEntity) {
		
		if (Optional.ofNullable(tweetImageEntity).isEmpty()) {
			throw new RepositoryException("Empty entity");
		}
		
		final var builder = TweetImage
				.builder()
				.id(tweetImageEntity.getId())
				.size(tweetImageEntity.getSize())
				.type(tweetImageEntity.getType())
				.width(tweetImageEntity.getWidth())
				.height(tweetImageEntity.getHeight());
		
		return builder.build();
	}
	
	public TweetImageJpaEntity mapDomainObject(final TweetImage tweetImage,
			final TweetJpaEntity tweetJpaEntity) {
		
		if (Optional.ofNullable(tweetImage).isEmpty()) {
			throw new RepositoryException("Empty domain object");
		}
		
		final var builder = TweetImageJpaEntity
				.builder()
				.id(tweetImage.id().id())
				.size(tweetImage.size().size())
				.type(tweetImage.type().type())
				.width(tweetImage.width().width())
				.height(tweetImage.height().height())
				.tweet(tweetJpaEntity);
		
		return builder.build();
	}
}