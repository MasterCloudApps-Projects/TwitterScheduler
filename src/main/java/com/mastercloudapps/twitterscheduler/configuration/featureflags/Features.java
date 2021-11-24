package com.mastercloudapps.twitterscheduler.configuration.featureflags;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;

public enum Features implements Feature {

	@EnabledByDefault
	@Label("Scheduler")
	SCHEDULER,
	
	@EnabledByDefault
	@Label("Publish on demand")
	PUBLISH_ON_DEMAND
}
