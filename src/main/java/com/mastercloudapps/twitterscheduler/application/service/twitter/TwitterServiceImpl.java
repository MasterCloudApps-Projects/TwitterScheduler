package com.mastercloudapps.twitterscheduler.application.service.twitter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.application.model.twitter.PublishTweetRequest;
import com.mastercloudapps.twitterscheduler.application.model.twitter.PublishTweetResponse;
import com.mastercloudapps.twitterscheduler.application.model.twitter.PublishedImageResponse;
import com.mastercloudapps.twitterscheduler.configuration.TwitterConfiguration;
import com.mastercloudapps.twitterscheduler.domain.exception.ServiceException;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.UploadedMedia;

@Component
public class TwitterServiceImpl implements TwitterService {
	
	private static final String ERR_MSG_PUBLISH_TWEET = "Error publishing in Twitter";

	@Autowired
	private TwitterConfiguration twitter;
	
	public TwitterServiceImpl(TwitterConfiguration twitter) {
		
		this.twitter = twitter;
	}
		
	@Override
	public Optional<PublishTweetResponse> publish(PublishTweetRequest request) {
		
		try {
		
			StatusUpdate statusUpdate = new StatusUpdate(request.getMessage());
			List<UploadedMedia> uploadedImages = Collections.emptyList();
			
			if (!request.getImages().isEmpty()) {
				uploadedImages = this.uploadImages(request);				
				statusUpdate.setMediaIds(this.getImageIds(uploadedImages));
			}
			
			Status status = this.twitter.getClient().updateStatus(statusUpdate);
			
			final var responseBuilder = PublishTweetResponse.builder()
					.id(status.getId())
					.url(this.getTweetUrl(status))
					.message(statusUpdate.getStatus())
					.publishedAt(status.getCreatedAt().toInstant());

			responseBuilder.images(this.getImages(uploadedImages));
			
			return Optional.of(responseBuilder.build());
			
		} catch (Exception e) {
			throw new ServiceException(ERR_MSG_PUBLISH_TWEET, e);
		}		
	}
	
	private String getTweetUrl(Status status) {
		
		return "https://twitter.com/" + status.getUser().getScreenName()
				+ "/status/" + status.getId();
	}
	
	private List<UploadedMedia> uploadImages(PublishTweetRequest request)
			throws MalformedURLException, IOException, TwitterException {
	
		List<UploadedMedia> uploadedImages = new ArrayList<>();
		for (int i=0; i<request.getImages().size(); i++) {
			InputStream imgInputStream = new URL(request.getImages().get(i)).openStream();
			UploadedMedia uploadedImage = this.twitter.getClient()
					.uploadMedia(UUID.randomUUID() + ".jpg", imgInputStream);
			uploadedImages.add(uploadedImage);
		}
		return uploadedImages;
	}
	
	private long[] getImageIds(List<UploadedMedia> uploadedImages) {
		
		long[] uploadedImagesIds = new long[uploadedImages.size()];
		for (int i = 0; i<uploadedImages.size(); i++) {
			uploadedImagesIds[i] = uploadedImages.get(i).getMediaId();
		}
		return uploadedImagesIds;
	}
	
	private List<PublishedImageResponse> getImages(List<UploadedMedia> uploadedImages) {
		
		List<PublishedImageResponse> images;
		if (!uploadedImages.isEmpty()) {
			images = uploadedImages.stream()
					.map(image -> PublishedImageResponse.builder()
							.id(image.getMediaId())
							.size(image.getSize())
							.type(image.getImageType())
							.width(image.getImageWidth())
							.height(image.getImageHeight())
							.build())
					.collect(Collectors.toList());
		} else {
			images = Collections.emptyList();
		}
		return images;
	}

}