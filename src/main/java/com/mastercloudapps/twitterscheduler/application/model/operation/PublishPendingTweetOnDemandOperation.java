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
public class PublishPendingTweetOnDemandOperation implements Operation {

	private static final long serialVersionUID = 7049469684542517024L;

	@NonNull
	private final Long id;

}