package com.mastercloudapps.twitterscheduler.controller.tweet.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class TweetResponse {

	private Long tweetId;
	private String message;
	private List<TweetImageResponse> images;
	private LocalDateTime requestedPublicationDate;
	private LocalDateTime publishedAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
