package com.mastercloudapps.twitterscheduler.controller;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.togglz.core.manager.FeatureManager;

import com.mastercloudapps.twitterscheduler.application.usecase.PublishPendingTweetsUseCase;
import com.mastercloudapps.twitterscheduler.configuration.featureflags.Features;
import com.mastercloudapps.twitterscheduler.domain.exception.ServiceException;
import com.mastercloudapps.twitterscheduler.domain.shared.NullableInstant;

@Configuration
@EnableScheduling
@EnableAsync
public class TweetPublisherTask {
	
	//TODO use spring profiles to run only in PRO
	private static final String ERR_MSG_IN_SERVICE_SCHEDULED_EXECUTION = "Error executing TweetPublisherTask ";
	
	private static Logger logger = LoggerFactory.getLogger(TweetPublisherTask.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	private FeatureManager featureManager;
	
	private PublishPendingTweetsUseCase useCase;

	public TweetPublisherTask(FeatureManager featureManager, final PublishPendingTweetsUseCase useCase) {
		
		this.featureManager = featureManager;
		this.useCase = useCase;
	}

	@Async
	@Scheduled(fixedRateString = "${scheduler.frequency}", initialDelayString = "${scheduler.initial.delay}")
    public void execute() {
		
		if (featureManager.isActive(Features.SCHEDULER)){
			try {
				//logger.info("Publisher is enabled. Current time {}", dateFormat.format(new Date()));
				logger.info("Publisher is enabled. Current time now is {}", NullableInstant.now().getFormatted());	
			} catch (Exception e) {
				throw new ServiceException(ERR_MSG_IN_SERVICE_SCHEDULED_EXECUTION, e);
			}
		} else {
			logger.info("Publisher not active {}", NullableInstant.now().getFormatted());
	}
	}

}
