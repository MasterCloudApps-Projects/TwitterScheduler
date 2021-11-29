package com.mastercloudapps.twitterscheduler.application.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.application.model.operation.CreatePendingTweetOperation;
import com.mastercloudapps.twitterscheduler.application.model.operation.DeletePendingTweetOperation;
import com.mastercloudapps.twitterscheduler.application.model.operation.FindOnePendingTweetOperation;
import com.mastercloudapps.twitterscheduler.application.usecase.CreatePendingTweetUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.DeletePendingTweetUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.FindAllPendingTweetUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.FindOnePendingTweetUseCase;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweet;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweetId;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweetImage;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweetImageId;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweetPort;
import com.mastercloudapps.twitterscheduler.domain.shared.NullableInstant;

@Component
public class PendingTweetService implements CreatePendingTweetUseCase, DeletePendingTweetUseCase, 
		FindAllPendingTweetUseCase, FindOnePendingTweetUseCase {

	private PendingTweetPort pendingTweetPort;
	
	
	public PendingTweetService(PendingTweetPort pendingTweetPort) {
		
		this.pendingTweetPort = pendingTweetPort;
	}
	
	@Override
	public PendingTweet create(CreatePendingTweetOperation request) {
		
		final var builder = PendingTweet.builder()
				.id(PendingTweetId.defaultValue())
				.message(request.getMessage())
				.publicationDate(request.getPublicationDate().instant())
				.createdAt(NullableInstant.now().instant());
		
		Optional.ofNullable(request.getImages())
			.ifPresent(images -> builder.images(images.stream()
					.map(image -> PendingTweetImage.builder()
							.id(PendingTweetImageId.defaultValue())
							.url(image)
							.build())
					.collect(Collectors.toList())));
		
		return pendingTweetPort.create(builder.build());
	}

	@Override
	public void delete(DeletePendingTweetOperation request) {
		
		pendingTweetPort.delete(request.getId());
		
	}

	@Override
	public Collection<PendingTweet> findAll() {
		
		return pendingTweetPort.findAll();
	}

	@Override
	public Optional<PendingTweet> findOne(FindOnePendingTweetOperation operation) {
		
		return pendingTweetPort.findOne(operation.getId());
	}

}