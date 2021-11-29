package com.mastercloudapps.twitterscheduler.controller.tweet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class TweetImageResponse {
	
	private Long id;
	private Long size;
	private String type;
	private Integer width;
	private Integer height;
	

}
