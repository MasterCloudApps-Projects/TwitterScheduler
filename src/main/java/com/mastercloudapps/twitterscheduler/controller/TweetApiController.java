package com.mastercloudapps.twitterscheduler.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mastercloudapps.twitterscheduler.controller.tweet.dto.TweetImageResponse;
import com.mastercloudapps.twitterscheduler.controller.tweet.dto.TweetResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/tweets")
@SecurityRequirement(name = "twitter-scheduler")
public class TweetApiController implements TweetApi {

	@GetMapping
	public Collection<TweetResponse> getTweets() {

		return Collections.emptyList();
	}

	@GetMapping("/{id}")
	public TweetResponse getTweetById(Long id) {
		
		LocalDateTime now = LocalDateTime.now();
		return TweetResponse.builder()
				.tweetId(id)
				.message("test message")
				.images(Arrays.asList(TweetImageResponse
						.builder()
						.tweetImageId(1453486161003954183L)
						.type("image/jpeg")
						.size(50734L)
						.height(512)
						.width(512)
						.build()))
				.createdAt(now)
				.requestedPublicationDate(now.minus(20,ChronoUnit.SECONDS))
				.publishedAt(now)
				.updatedAt(now)
				.build();
	}

}
