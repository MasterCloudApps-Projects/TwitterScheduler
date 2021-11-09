package com.mastercloudapps.twitterscheduler.application.usecase;

import java.util.Collection;

import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweet;

public interface FindAllPendingTweetUseCase {
	
	public Collection<PendingTweet> findAll();

}
