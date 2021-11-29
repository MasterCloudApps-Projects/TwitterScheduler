package com.mastercloudapps.twitterscheduler.controller.tweet.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class TweetResponseMapperTest {
	
	private TweetResponseMapper mapper;

	@BeforeEach
	void setUp() {
		mapper = new TweetResponseMapper();
	}

}