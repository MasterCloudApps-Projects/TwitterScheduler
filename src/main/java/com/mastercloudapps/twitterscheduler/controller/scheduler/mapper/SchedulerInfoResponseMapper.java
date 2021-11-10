package com.mastercloudapps.twitterscheduler.controller.scheduler.mapper;

import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.application.model.scheduler.SchedulerInfo;
import com.mastercloudapps.twitterscheduler.controller.scheduler.dto.SchedulerInfoResponse;

@Component
public class SchedulerInfoResponseMapper {

	public SchedulerInfoResponse mapResponse(SchedulerInfo schedulerInfo) {

		var responseBuilder = SchedulerInfoResponse
				.builder()
				.active(schedulerInfo.isActive())
				.fixedRate(schedulerInfo.getFixedRate())
				.initialDelay(schedulerInfo.getInitialDelay());

		return responseBuilder.build();
	}
}
