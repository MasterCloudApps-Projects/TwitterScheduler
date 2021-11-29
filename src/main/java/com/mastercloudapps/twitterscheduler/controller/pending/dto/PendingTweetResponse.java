package com.mastercloudapps.twitterscheduler.controller.pending.dto;

import java.util.List;

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
	private String publicationDate;
	private String createdAt;
	private List<PendingImageResponse> images;

}
