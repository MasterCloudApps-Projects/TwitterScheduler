package com.mastercloudapps.twitterscheduler.application.model.twitter;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class PublishedImageResponse {

	private Long id;

	private Long size;

	private String type;

	private Integer width;

	private Integer height;
}