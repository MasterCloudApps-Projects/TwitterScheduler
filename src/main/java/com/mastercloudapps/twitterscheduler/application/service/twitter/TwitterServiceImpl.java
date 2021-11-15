package com.mastercloudapps.twitterscheduler.application.service.twitter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.application.model.twitter.PublishTweetRequest;
import com.mastercloudapps.twitterscheduler.application.model.twitter.PublishTweetResponse;
import com.mastercloudapps.twitterscheduler.configuration.TwitterConfiguration;
import com.mastercloudapps.twitterscheduler.domain.exception.ServiceException;

import twitter4j.Status;
import twitter4j.StatusUpdate;

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
			Status status = this.twitter.getClient().updateStatus(statusUpdate);
			
			PublishTweetResponse response = PublishTweetResponse.builder()
					.id(status.getId())
					.url(this.getTweetUrl(status))
					.message(statusUpdate.getStatus())
					.publishedAt(status.getCreatedAt().toInstant())
					.build();
			
			return Optional.of(response);
			
		} catch (Exception e) {
			throw new ServiceException(ERR_MSG_PUBLISH_TWEET, e);
		}		
	}
	
	private String getTweetUrl(Status status) {
		
		return "https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();
	}
	
}
