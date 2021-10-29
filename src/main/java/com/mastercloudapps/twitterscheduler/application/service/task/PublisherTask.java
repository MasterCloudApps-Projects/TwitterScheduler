package com.mastercloudapps.twitterscheduler.application.service.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.togglz.core.manager.FeatureManager;

import com.mastercloudapps.twitterscheduler.configuration.featureflags.Features;

@Configuration
@EnableScheduling
@EnableAsync
public class PublisherTask {
	
	//TODO use spring profiles to run only in PRO
	
	private static Logger logger = LoggerFactory.getLogger(PublisherTask.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	private FeatureManager featureManager;

	public PublisherTask(FeatureManager featureManager) {
		this.featureManager = featureManager;
	}

	@Async
	@Scheduled(fixedRateString = "PT20S")
    public void execute() {
		
		if (featureManager.isActive(Features.SCHEDULER)){
			logger.info("Publisher is enabled. Current time {}", dateFormat.format(new Date()));
		}
	}

}
