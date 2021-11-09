package com.mastercloudapps.twitterscheduler.application.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.application.model.operation.FindOneTweetOperation;
import com.mastercloudapps.twitterscheduler.application.usecase.FindAllTweetUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.FindOneTweetUseCase;
import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;
import com.mastercloudapps.twitterscheduler.domain.tweet.TweetPort;

@Component
public class TweetService implements FindAllTweetUseCase, FindOneTweetUseCase {

	private TweetPort tweetPort;
	
	public TweetService(TweetPort tweetPort) {
		
		this.tweetPort = tweetPort;
	}
	
	@Override
	public Collection<Tweet> findAll() {
		
		return tweetPort.findAll();
	}

	@Override
	public Optional<Tweet> findOne(FindOneTweetOperation operation) {
		
		return tweetPort.findOne(operation.getId());
	}

}
