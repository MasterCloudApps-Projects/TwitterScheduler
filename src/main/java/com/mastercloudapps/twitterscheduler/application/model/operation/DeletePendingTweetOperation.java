package com.mastercloudapps.twitterscheduler.application.model.operation;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class DeletePendingTweetOperation implements Operation {

	private static final long serialVersionUID = 2558747427860900262L;
	
	@NonNull
	private final Long id;

	
}
