package com.mastercloudapps.twitterscheduler.mocks;

import com.mastercloudapps.twitterscheduler.application.model.scheduler.SchedulerInfo;

public enum SchedulerInfoData {

	ACTIVE(true, 180000L, 60000L),
	INACTIVE(false, 180000L, 60000L);
	
	private final boolean active;

	private final Long fixedRate;

	private final Long initialDelay;
	
	SchedulerInfoData(final boolean active, final Long fixedRate, final Long initialDelay) {

		this.active = active;
		this.fixedRate = fixedRate;
		this.initialDelay = initialDelay;
	}
	
	public SchedulerInfo create() {

		return SchedulerInfo.builder()
				.active(this.active)
				.fixedRate(this.fixedRate)
				.initialDelay(this.initialDelay)
				.build();
	}	

}
