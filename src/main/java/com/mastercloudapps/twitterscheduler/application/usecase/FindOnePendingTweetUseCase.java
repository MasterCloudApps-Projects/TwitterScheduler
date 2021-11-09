package com.mastercloudapps.twitterscheduler.application.usecase;

import java.util.Optional;

import com.mastercloudapps.twitterscheduler.application.model.operation.FindOnePendingTweetOperation;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweet;

public interface FindOnePendingTweetUseCase {

	public Optional<PendingTweet> findOne(FindOnePendingTweetOperation operation);
}
