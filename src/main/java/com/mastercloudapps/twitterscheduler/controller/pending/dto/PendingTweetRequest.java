package com.mastercloudapps.twitterscheduler.controller.pending.dto;

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
public class PendingTweetRequest {

	private String message;
	private List<PendingImageRequest> images;
	private LocalDateTime publicationDate;
	
}
