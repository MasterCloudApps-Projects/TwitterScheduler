package com.mastercloudapps.twitterscheduler.application.usecase;

import java.util.Optional;

import com.mastercloudapps.twitterscheduler.application.model.operation.PublishPendingTweetOnDemandOperation;
import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;

public interface PublishPendingTweetOnDemandUseCase {

	public Optional<Tweet> publishImmediatly(PublishPendingTweetOnDemandOperation operation);
	
}
