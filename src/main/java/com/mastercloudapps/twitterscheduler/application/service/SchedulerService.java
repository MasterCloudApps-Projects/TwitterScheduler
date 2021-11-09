package com.mastercloudapps.twitterscheduler.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;

import com.mastercloudapps.twitterscheduler.application.model.scheduler.SchedulerConfiguration;
import com.mastercloudapps.twitterscheduler.application.model.scheduler.SchedulerInfo;
import com.mastercloudapps.twitterscheduler.application.usecase.GetSchedulerInfoUseCase;
import com.mastercloudapps.twitterscheduler.configuration.featureflags.Features;
import com.mastercloudapps.twitterscheduler.domain.exception.ServiceException;

@Component
public class SchedulerService implements GetSchedulerInfoUseCase {

	private static final String ERR_MSG_GETTING_SCHEDULER_INFO = "Error getting scheduler info";
	
	private SchedulerConfiguration schedulerConfiguration;
			
	private FeatureManager featureManager;

	@Autowired
	public SchedulerService(FeatureManager featureManager,
			SchedulerConfiguration schedulerConfiguration) {
		
		this.featureManager = featureManager;
		this.schedulerConfiguration = schedulerConfiguration;
	}
	
	@Override
	public Optional<SchedulerInfo> getInfo() {
		
		try {
			SchedulerInfo info = SchedulerInfo.builder()
					.active(featureManager.isActive(Features.SCHEDULER))
					.fixedRate(Long.parseLong(schedulerConfiguration.getFixedRate()))
					.initialDelay(Long.parseLong(schedulerConfiguration.getInitialDelay()))					
					.build();
			return Optional.of(info);

		} catch (Exception e) {
			throw new ServiceException(ERR_MSG_GETTING_SCHEDULER_INFO, e);
		}
	}

}
