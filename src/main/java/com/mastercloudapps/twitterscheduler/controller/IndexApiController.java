package com.mastercloudapps.twitterscheduler.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/")
@SecurityRequirement(name = "twitter-scheduler")
public class IndexApiController implements IndexApi {
	
	@GetMapping
	public ResponseEntity<String> getIndex() {

		return new ResponseEntity<>("Hello from TwitterScheduler app!", HttpStatus.OK);
	}

}
