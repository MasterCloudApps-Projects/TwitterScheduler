package com.mastercloudapps.twitterscheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mastercloudapps.twitterscheduler.application.usecase.GetSchedulerInfoUseCase;
import com.mastercloudapps.twitterscheduler.controller.scheduler.dto.SchedulerInfoResponse;
import com.mastercloudapps.twitterscheduler.controller.scheduler.mapper.SchedulerInfoResponseMapper;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/scheduler")
@SecurityRequirement(name = "twitter-scheduler")
public class SchedulerApiController implements SchedulerApi {

	private final GetSchedulerInfoUseCase getSchedulerInfoUseCase;
	
	private final SchedulerInfoResponseMapper responseMapper;
	
	@Autowired
	public SchedulerApiController(final GetSchedulerInfoUseCase getSchedulerInfoUseCase,
			final SchedulerInfoResponseMapper responseMapper) {
		
		this.getSchedulerInfoUseCase = getSchedulerInfoUseCase;
		this.responseMapper = responseMapper;
	}
	
	@GetMapping("/info")
	public ResponseEntity<SchedulerInfoResponse> getInfo() {

		final var schedulerInfo = getSchedulerInfoUseCase.getInfo();
		
		if(schedulerInfo.isPresent()) {
			return new ResponseEntity<>(responseMapper.mapResponse(schedulerInfo.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
