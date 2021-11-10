package com.mastercloudapps.twitterscheduler.application.model.twitter;

import java.time.Instant;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class PublishTweetResponse {

	private Long id;
	
	private String message;
	
	private String url;
	
	private Instant publishedAt;
}
