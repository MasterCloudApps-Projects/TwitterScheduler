package com.mastercloudapps.twitterscheduler.infrastructure.postgre.pending;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.domain.exception.RepositoryException;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweet;

@Component
public class PendingTweetJpaMapper {
	
	public PendingTweet mapEntity(final PendingTweetJpaEntity pendingTweetEntity) {
		
		if (Optional.ofNullable(pendingTweetEntity).isEmpty()) {
			throw new RepositoryException("Empty entity");
		}
		
		return PendingTweet
				.builder()
				.id(pendingTweetEntity.getId())
				.message(pendingTweetEntity.getMessage())
				.publicationDate(pendingTweetEntity.getPublicationDate())
				.createdAt(pendingTweetEntity.getCreatedAt())
				.build();
	}
	
	public PendingTweetJpaEntity mapCreatePendingTweet(final PendingTweet pendingTweet) {
		
		if (Optional.ofNullable(pendingTweet).isEmpty()) {
			throw new RepositoryException("Empty domain object");
		}
		
		return PendingTweetJpaEntity
				.builder()
				.message(pendingTweet.message().message())
				.publicationDate(pendingTweet.publicationDate().instant())
				.createdAt(pendingTweet.createdAt().instant())
				.build();
	}
	
	public PendingTweetJpaEntity mapDomainObject(final PendingTweet pendingTweet) {
		
		if (Optional.ofNullable(pendingTweet).isEmpty()) {
			throw new RepositoryException("Empty domain object");
		}
		
		return PendingTweetJpaEntity
				.builder()
				.id(pendingTweet.id().id())
				.message(pendingTweet.message().message())
				.publicationDate(pendingTweet.publicationDate().instant())
				.createdAt(pendingTweet.createdAt().instant())
				.build();
	}

}
