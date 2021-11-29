package com.mastercloudapps.twitterscheduler.controller.pending.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class PendingImageResponse {
	
	private Long id;
	private String url;

}
