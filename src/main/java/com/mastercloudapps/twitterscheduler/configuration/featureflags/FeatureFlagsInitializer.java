package com.mastercloudapps.twitterscheduler.configuration.featureflags;

import javax.annotation.PostConstruct;

import org.ff4j.FF4j;
import org.springframework.stereotype.Component;

@Component
public class FeatureFlagsInitializer {

	public static String FEATURE_ENABLE_PUBLISHER = "enablePublisher";

	private FF4j ff4j;	

	public FeatureFlagsInitializer(FF4j ff4j) {
		this.ff4j = ff4j;
	}

	@PostConstruct
	public void initializeFlags() {

		if (!ff4j.exist(FEATURE_ENABLE_PUBLISHER)) {
			ff4j.createFeature(FEATURE_ENABLE_PUBLISHER, true);
		}
	}
}
