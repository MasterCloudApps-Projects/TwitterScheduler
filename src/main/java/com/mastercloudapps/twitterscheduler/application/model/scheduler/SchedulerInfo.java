package com.mastercloudapps.twitterscheduler.application.model.scheduler;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class SchedulerInfo {
	
	private boolean active;
	
	private Long fixedRate;
	
	private Long initialDelay;

}
