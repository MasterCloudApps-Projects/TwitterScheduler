package com.mastercloudapps.twitterscheduler.controller.pending.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class PendingTweetResponse {

	private Long id;
	private String message;
	//private List<PendingImageResponse> images;
	private String publicationDate;
	private String createdAt;

}
