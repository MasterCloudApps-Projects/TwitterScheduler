package com.mastercloudapps.twitterscheduler.application.model.operation;

import java.util.List;

import com.mastercloudapps.twitterscheduler.domain.shared.NullableInstant;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class CreatePendingTweetOperation implements Operation {

	private static final long serialVersionUID = 3099486578059492608L;

	@NonNull
	private final String message;

	@NonNull
	private final NullableInstant publicationDate;
	
	private final List<String> images;

}
