package com.mastercloudapps.twitterscheduler.domain.tweet;

import java.util.Collection;
import java.util.Optional;

public interface TweetPort {

	public Tweet create(Tweet tweet);
	
	public void delete(Long id);
	
	public Collection<Tweet> findAll();
	
	public Optional<Tweet> findOne(Long id);

}
