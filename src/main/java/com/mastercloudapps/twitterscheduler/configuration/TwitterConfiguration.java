package com.mastercloudapps.twitterscheduler.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

@Component
public class TwitterConfiguration {
	
	@Value("${twitter.oauth.consumerKey}")
	private String consumerKey;
	
	@Value("${twitter.oauth.consumerSecret}")
	private String consumerSecret;
	
	@Value("${twitter.oauth.accessToken}")
	private String accessToken;
	
	@Value("${twitter.oauth.accessTokenSecret}")
	private String accessTokenSecret;
	
	private Twitter twitter;
	
	public TwitterConfiguration() {
		
	}
	
	public Twitter getClient() {
		
		if (twitter == null) {
			twitter = new TwitterFactory().getInstance();
			
			twitter.setOAuthConsumer(consumerKey, consumerSecret);
			
			AccessToken oathAccessToken = new AccessToken(accessToken, accessTokenSecret);
			twitter.setOAuthAccessToken(oathAccessToken);
		}
		
		return twitter;
	}

}
