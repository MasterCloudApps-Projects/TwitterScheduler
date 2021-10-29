package com.mastercloudapps.twitterscheduler.configuration.featureflags;

import org.togglz.core.Feature;
import org.togglz.core.annotation.Label;

public enum Features implements Feature {

	@Label("Scheduler")
	SCHEDULER
}
