package com.mastercloudapps.twitterscheduler.controller.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SchedulerResponse {

	private boolean active;
	
}
