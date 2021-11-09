package com.mastercloudapps.twitterscheduler.application.model.scheduler;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public class SchedulerConfiguration {

	@Value("${scheduler.frequency}")
	private String fixedRate;
	
	@Value("${scheduler.initial.delay}")
	private String initialDelay;
	
	public Instant publishUntil() {
		
		return Instant.now()
				.plusMillis(Long.valueOf(fixedRate));
	}
	
}
