package com.mastercloudapps.twitterscheduler.application.usecase;

import java.util.Collection;

import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;

public interface FindAllTweetUseCase {

	public Collection<Tweet> findAll();
}
