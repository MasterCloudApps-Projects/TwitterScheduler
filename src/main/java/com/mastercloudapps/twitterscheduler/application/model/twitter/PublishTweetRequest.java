package com.mastercloudapps.twitterscheduler.application.model.twitter;

import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class PublishTweetRequest {

	private String message;
	
	private List<String> images;

}
