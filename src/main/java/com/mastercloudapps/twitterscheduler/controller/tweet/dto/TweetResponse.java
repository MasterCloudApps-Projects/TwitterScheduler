package com.mastercloudapps.twitterscheduler.controller.tweet.dto;

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

	private Long id;
	private String message;
	private String url;
	private String requestedPublicationDate;
	private String publishedAt;
	private String createdAt;
	private String publicationType;
	private List<TweetImageResponse> images;

}
