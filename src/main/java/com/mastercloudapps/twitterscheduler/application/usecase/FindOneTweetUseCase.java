package com.mastercloudapps.twitterscheduler.application.usecase;

import java.util.Optional;

import com.mastercloudapps.twitterscheduler.application.model.operation.FindOneTweetOperation;
import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;

public interface FindOneTweetUseCase {

	public Optional<Tweet> findOne(FindOneTweetOperation operation);
}
