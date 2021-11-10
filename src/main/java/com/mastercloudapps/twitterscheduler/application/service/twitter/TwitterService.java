package com.mastercloudapps.twitterscheduler.application.service.twitter;

import java.util.Optional;

import com.mastercloudapps.twitterscheduler.application.model.twitter.PublishTweetRequest;
import com.mastercloudapps.twitterscheduler.application.model.twitter.PublishTweetResponse;

public interface TwitterService {

	public Optional<PublishTweetResponse> publish(PublishTweetRequest request);
}
