package com.mastercloudapps.twitterscheduler.application.service.task;

import static com.mastercloudapps.twitterscheduler.configuration.featureflags.FeatureFlagsInitializer.FEATURE_ENABLE_PUBLISHER;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ff4j.FF4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@EnableAsync
public class PublisherTask {
	
	//TODO use spring profiles to run only in PRO
	
	private static Logger logger = LoggerFactory.getLogger(PublisherTask.class);
	
	private FF4j ff4j;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	public PublisherTask(FF4j ff4j) {
		this.ff4j = ff4j;
	}

	@Async
	@Scheduled(fixedRateString = "PT20S")
    public void execute() {
		
		if (ff4j.check(FEATURE_ENABLE_PUBLISHER)){
			logger.info("Publisher is enabled. Current time {}", dateFormat.format(new Date()));
		}
	}

}
