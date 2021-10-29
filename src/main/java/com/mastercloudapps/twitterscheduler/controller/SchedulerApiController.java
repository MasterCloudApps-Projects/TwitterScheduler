package com.mastercloudapps.twitterscheduler.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mastercloudapps.twitterscheduler.controller.dto.scheduler.SchedulerResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/scheduler")
@SecurityRequirement(name = "twitter-scheduler")
public class SchedulerApiController implements SchedulerApi {

	@GetMapping("/status")
	public ResponseEntity<SchedulerResponse> getStatus() {

		return ResponseEntity.ok(SchedulerResponse.builder()
				.active(true)
				.build());
	}

}
