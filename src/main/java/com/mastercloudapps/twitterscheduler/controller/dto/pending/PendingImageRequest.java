package com.mastercloudapps.twitterscheduler.controller.dto.pending;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class PendingImageRequest {

	private String url;
	
}
