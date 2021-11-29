package com.mastercloudapps.twitterscheduler.controller.tweet.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.togglz.core.manager.FeatureManager;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class TweetResponseMapperTest {
	
	private TweetResponseMapper mapper;
	
	@Mock
	private FeatureManager featureManager;
	
	@BeforeEach
	void setUp() {
		mapper = new TweetResponseMapper(featureManager);
	}

}