package com.mastercloudapps.twitterscheduler.controller.pending.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class PendingTweetRequest {

	@Schema(description = "Tweet content", 
            example = "This is a test tweet", required = true)
	private String message;
	
	@Schema(description = "Publication date", 
            example = "2022-04-01T10:00:00Z", required = true)
	private String publicationDate;
	
	@Schema(description = "Images, specify url of each image", required = false)
	private List<PendingImageRequest> images;
	
}
