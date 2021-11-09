package com.mastercloudapps.twitterscheduler.mocks;

import java.time.Instant;

import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweet;

public enum PendingTweetData {
	HAPPY_NEW_YEAR(
			1L,
			"Happy new year!",
			Instant.parse(Constants.PUBLICATION_DATE_NEW_YEAR_2023),
			Instant.parse(Constants.CREATED_AT_NEW_YEAR_2023)),
	MERRY_CHRISTMAS(
			1L,
			"Merry Christmas!",
			Instant.parse(Constants.PUBLICATION_DATE_CHRISTMAS_2024),
			Instant.parse(Constants.CREATED_AT_CHRISTMAS_2024));

	private final Long id;

	private final String message;

	private final Instant publicationDate;
	
	private final Instant createdAt;

	PendingTweetData(final Long id, final String message, final Instant publicationDate,
			final Instant createdAt) {

		this.id = id;
		this.message = message;
		this.publicationDate = publicationDate;
		this.createdAt = createdAt;
	}

	public PendingTweet create() {

		return PendingTweet.builder()
				.id(this.id)
				.message(this.message)
				.publicationDate(this.publicationDate)
				.createdAt(this.createdAt)
				.build();
	}

	public static class Constants {

		// ISO-8601 string
		public static final String PUBLICATION_DATE_NEW_YEAR_2023 = "2023-01-01T00:00:00Z";

		public static final String PUBLICATION_DATE_CHRISTMAS_2024 = "2024-12-25T00:00:00Z";
		
		public static final String CREATED_AT_NEW_YEAR_2023 = "2021-10-01T00:00:00Z";
		
		public static final String CREATED_AT_CHRISTMAS_2024 = "2021-11-01T00:00:00Z";

		private Constants() {}
	}
}
