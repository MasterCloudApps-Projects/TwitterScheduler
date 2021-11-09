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
public class FindOneTweetOperation implements Operation {

	private static final long serialVersionUID = 4005439245053292442L;
	
	@NonNull
	private final Long id;

	
}
