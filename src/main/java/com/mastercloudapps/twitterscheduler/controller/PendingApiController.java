package com.mastercloudapps.twitterscheduler.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingImageResponse;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingTweetRequest;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingTweetResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/pending")
@SecurityRequirement(name = "twitter-scheduler")
public class PendingApiController implements PendingApi {

	@GetMapping
	public Collection<PendingTweetResponse> getPendingTweets() {
		
		return Collections.emptyList();
	}

	@GetMapping("/{id}")
	public PendingTweetResponse getPendingTweetById(Long id) {

		return getDummyResponse(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PendingTweetResponse postPendingTweet(PendingTweetRequest request) {

		return getDummyResponse(505L);
	}

	@DeleteMapping("/{id}")
	public PendingTweetResponse deletePendingTweet(Long id) {
		
		return getDummyResponse(id);
	}
	
	private PendingTweetResponse getDummyResponse(Long id) {
		
		LocalDateTime now = LocalDateTime.now();
		return PendingTweetResponse.builder()
				.pendingTweetId(id)
				.message("Status message")
				.images(Arrays.asList(PendingImageResponse.builder()
						.pendingImageId(5555L)
						.url("https://davidrojo.eu/images/tfm/1.jpg")
						.build()))
				.publicationDate(now.plus(20,ChronoUnit.MINUTES))
				.createdAt(now)
				.updatedAt(now)
				.build();
	}

}
