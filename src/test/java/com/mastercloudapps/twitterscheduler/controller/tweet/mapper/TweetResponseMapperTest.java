package com.mastercloudapps.twitterscheduler.controller.tweet.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TweetResponseMapperTest {
	
	private TweetResponseMapper mapper;
	
	@BeforeEach
	void setUp() {
		mapper = new TweetResponseMapper();
	}

}
