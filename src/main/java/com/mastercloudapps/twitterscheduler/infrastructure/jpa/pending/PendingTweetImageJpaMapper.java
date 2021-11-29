package com.mastercloudapps.twitterscheduler.infrastructure.jpa.pending;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.domain.exception.RepositoryException;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweetImage;

@Component
public class PendingTweetImageJpaMapper {

	public PendingTweetImage mapEntity(final PendingTweetImageJpaEntity pendingTweetImageEntity) {
		
		if (Optional.ofNullable(pendingTweetImageEntity).isEmpty()) {
			throw new RepositoryException("Empty entity");
		}
		
		final var builder = PendingTweetImage
				.builder()
				.id(pendingTweetImageEntity.getId())
				.url(pendingTweetImageEntity.getUrl());
				
		return builder.build();
	}
	
	public PendingTweetImageJpaEntity mapCreatePendingTweetImage(final PendingTweetImage pendingTweetImage,
			final PendingTweetJpaEntity pendingTweetJpaEntity) {
		
		if (Optional.ofNullable(pendingTweetImage).isEmpty()) {
			throw new RepositoryException("Empty domain object");
		}
		
		final var builder = PendingTweetImageJpaEntity
				.builder()
				.url(pendingTweetImage.url().url())
				.pendingTweet(pendingTweetJpaEntity);
		
		return builder.build();
	}
	
	public PendingTweetImageJpaEntity mapDomainObject(final PendingTweetImage pendingTweetImage) {
		
		if (Optional.ofNullable(pendingTweetImage).isEmpty()) {
			throw new RepositoryException("Empty domain object");
		}
		
		final var builder = PendingTweetImageJpaEntity
				.builder()
				.id(pendingTweetImage.id().id())
				.url(pendingTweetImage.url().url());
		
		return builder.build();
	}
}