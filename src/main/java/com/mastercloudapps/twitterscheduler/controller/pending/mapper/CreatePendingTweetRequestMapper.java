package com.mastercloudapps.twitterscheduler.controller.pending.mapper;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;

import com.mastercloudapps.twitterscheduler.application.model.operation.CreatePendingTweetOperation;
import com.mastercloudapps.twitterscheduler.configuration.featureflags.Features;
import com.mastercloudapps.twitterscheduler.controller.exception.ExpiredPublicationDateException;
import com.mastercloudapps.twitterscheduler.controller.exception.ImageNotAvailableException;
import com.mastercloudapps.twitterscheduler.controller.exception.InvalidInputException;
import com.mastercloudapps.twitterscheduler.controller.exception.MalformedImageUrlException;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingImageRequest;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingTweetRequest;
import com.mastercloudapps.twitterscheduler.controller.validator.ImageAvailable;
import com.mastercloudapps.twitterscheduler.domain.shared.NullableInstant;

@Component
public class CreatePendingTweetRequestMapper {
	
	private FeatureManager featureManager;
	
	private ImageAvailable imageAvailableValidator;
	
	@Autowired
	public CreatePendingTweetRequestMapper(final FeatureManager featureManager,
			final ImageAvailable imageAvailableValidator) {
		this.featureManager = featureManager;
		this.imageAvailableValidator = imageAvailableValidator;
	}

	public CreatePendingTweetOperation mapRequest(final PendingTweetRequest request) {

		if (Optional.ofNullable(request).isEmpty()) {
			throw new InvalidInputException("Invalid payload");
		}

		final var message = this.mapMessage(request);
		final var publicationDate = this.mapPublicationDate(request);

		final var builder =  CreatePendingTweetOperation
				.builder()
				.message(message)
				.publicationDate(publicationDate);
		
		if(featureManager.isActive(Features.TWEETS_WITH_IMAGES)) {
			final var images = this.mapImages(request);
			builder.images(images);
		}

		return builder.build();
	}

	private String mapMessage(final PendingTweetRequest request) {

		if (request.getMessage().equalsIgnoreCase("")) {
			throw new InvalidInputException("Missing required message");
		}
		return request.getMessage();
	}

	private NullableInstant mapPublicationDate(final PendingTweetRequest request) {

		if (request.getPublicationDate().equalsIgnoreCase("")) {
			throw new InvalidInputException("Missing required publicationDate");
		}
		
		
		String pubDate = requireNonNull(request.getPublicationDate(), "Publication date cannot be null.");
		NullableInstant instantPubDate = NullableInstant.fromUtcISO8601(pubDate);
		NullableInstant niNow = NullableInstant.now();
		if (instantPubDate.instant().isBefore(niNow.instant())) {
			throw new ExpiredPublicationDateException(instantPubDate, niNow);
		}
		
		return instantPubDate;
	}
	
	private List<String> mapImages(final PendingTweetRequest request){
		
		List<String> imagesOp = new ArrayList<>();
		
		if (Optional.ofNullable(request.getImages()).isPresent()) {
			for (PendingImageRequest imageRequest : request.getImages()) {
				try {
					boolean available = imageAvailableValidator.validate(imageRequest.getUrl());
					if (!available) {
						throw new ImageNotAvailableException(imageRequest.getUrl());
					}
					imagesOp.add(imageRequest.getUrl());
				}
				catch (IOException e) {
					throw new MalformedImageUrlException(imageRequest.getUrl());
				}
				
			}
		}
		return imagesOp;
	}
}