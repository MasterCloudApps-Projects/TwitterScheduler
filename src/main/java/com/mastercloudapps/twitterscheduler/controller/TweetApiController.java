package com.mastercloudapps.twitterscheduler.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mastercloudapps.twitterscheduler.application.usecase.FindAllTweetUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.FindOneTweetUseCase;
import com.mastercloudapps.twitterscheduler.controller.tweet.dto.TweetResponse;
import com.mastercloudapps.twitterscheduler.controller.tweet.mapper.FindOneTweetRequestMapper;
import com.mastercloudapps.twitterscheduler.controller.tweet.mapper.TweetResponseMapper;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/tweets")
@SecurityRequirement(name = "twitter-scheduler")
public class TweetApiController implements TweetApi {

	private final FindAllTweetUseCase findAllTweetUseCase;
	
	private final FindOneTweetUseCase findOneTweetUseCase;
	
	private final FindOneTweetRequestMapper findOneTweetRequestMapper;
	
	private final TweetResponseMapper responseMapper;
	
	public TweetApiController(final FindAllTweetUseCase findAllTweetUseCase,
			final TweetResponseMapper responseMapper, final FindOneTweetUseCase findOneTweetUseCase,
			final FindOneTweetRequestMapper findOneTweetRequestMapper) {
		
		this.findAllTweetUseCase = findAllTweetUseCase;
		this.responseMapper = responseMapper;
		this.findOneTweetUseCase = findOneTweetUseCase;
		this.findOneTweetRequestMapper = findOneTweetRequestMapper;
	}
	
	@GetMapping
	public Collection<TweetResponse> getTweets() {

		return findAllTweetUseCase.findAll().stream()
				.map(pendingTweet -> responseMapper.mapResponse(pendingTweet))
				.collect(Collectors.toList());	
	}

	@GetMapping("/{id}")
	public ResponseEntity<TweetResponse> getTweetById(Long id) {
		
		final var tweet = findOneTweetUseCase.findOne(
				findOneTweetRequestMapper.mapRequest(id));
		
		if(tweet.isPresent()) {
			return new ResponseEntity<>(responseMapper.mapResponse(tweet.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
