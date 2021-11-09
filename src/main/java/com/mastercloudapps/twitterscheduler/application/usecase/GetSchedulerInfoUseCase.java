package com.mastercloudapps.twitterscheduler.application.usecase;

import java.util.Optional;

import com.mastercloudapps.twitterscheduler.application.model.scheduler.SchedulerInfo;

public interface GetSchedulerInfoUseCase {

	public Optional<SchedulerInfo> getInfo();
}
