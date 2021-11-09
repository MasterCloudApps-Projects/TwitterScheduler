package com.mastercloudapps.twitterscheduler.domain.tweet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.mastercloudapps.twitterscheduler.domain.exception.MessageMaxLengthExceededException;
import com.mastercloudapps.twitterscheduler.domain.shared.NullableInstant;

class TweetTest {

	private Tweet createTweet(final MockData mockData) {

		return Tweet.builder()
				.id(mockData.tweetId)
				.message(mockData.message)
				.url(mockData.url)
				.requestedPublicationDate(mockData.requestedPublicationDate)
				.publishedAt(mockData.publishedAt)
				.createdAt(mockData.createdAt)
				.build();
	}

	enum MockData {
		VALID(
				1L,
				"valid message 1",
				"https://twitter.com/username/status/123456789",
				LocalDateTime.of(2030, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
				LocalDateTime.of(2030, 1, 1, 0, 3).toInstant(ZoneOffset.UTC),
				NullableInstant.now().instant()),
		VALID_OTHER_SAME_ID(
				1L, 
				"valid message 2",
				"https://twitter.com/username/status/123456798",
				LocalDateTime.of(2031, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
				LocalDateTime.of(2031, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
				NullableInstant.now().instant()),
		VALID_OTHER_DIFFERENT_ID(
				2L, 
				"valid message 3", 
				"https://twitter.com/username/status/123456897",
				LocalDateTime.of(2032, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
				LocalDateTime.of(2032, 1, 1, 0, 4).toInstant(ZoneOffset.UTC),
				NullableInstant.now().instant()),
		INVALID_NULL_ID(
				null, 
				"abc",
				"https://twitter.com/username/status/123456789",
				Instant.MAX,
				Instant.MAX,
				NullableInstant.now().instant()),
		INVALID_NULL_MESSAGE(
				1L,
				null,
				"https://twitter.com/username/status/123456789",
				Instant.MAX,
				Instant.MAX,
				NullableInstant.now().instant()),
		INVALID_NULL_URL(
				1L,
				"nmo",
				null,
				Instant.MAX,
				Instant.MAX,
				NullableInstant.now().instant()),
		INVALID_NULL_REQUESTED_PUBLICATION_DATE(
				1L,
				"xyz",
				"https://twitter.com/username/status/123456789",
				null,
				Instant.MAX,
				NullableInstant.now().instant()),
		INVALID_NULL_PUBLISHED_AT(
				1L,
				"ijk",
				"https://twitter.com/username/status/123456789",
				Instant.MAX,
				Instant.MAX,
				null),
		INVALID_NULL_CREATED_AT(
				1L,
				"ijk",
				"https://twitter.com/username/status/123456789",
				Instant.MAX,
				null,
				NullableInstant.now().instant()),
		INVALID_EXPIRED_REQUESTED_PUBLICATION_DATE(
				1L,
				"asdf",
				"https://twitter.com/username/status/123456789",
				LocalDateTime.of(2000, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
				Instant.MAX,
				NullableInstant.now().instant()),
		INVALID_EXPIRED_PUBLISHED_AT(
				1L,
				"asdf",
				"https://twitter.com/username/status/123456789",
				LocalDateTime.of(2032, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
				LocalDateTime.of(2000, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
				NullableInstant.now().instant()),
		INVALID_MESSAGE_MAX_LENGTH_EXCEEDED(
				1L,
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the "
						+ "industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type "
						+ "and scrambled it to make a type specimen book. It has survived not only five years.",
				"https://twitter.com/username/status/123456789",
				LocalDateTime.of(2033, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
				LocalDateTime.of(2033, 1, 1, 0, 5).toInstant(ZoneOffset.UTC),
				NullableInstant.now().instant());

		private final Long tweetId;

		private final String message;
		
		private final String url;

		private final Instant requestedPublicationDate;
		
		private final Instant publishedAt;
		
		private final Instant createdAt;

		MockData(final Long tweetId, final String message, final String url, 
				final Instant requestedPublicationDate, final Instant publishedAt,
				final Instant createdAt) {
			this.tweetId = tweetId;
			this.message = message;
			this.url = url;
			this.requestedPublicationDate = requestedPublicationDate;
			this.publishedAt = publishedAt;
			this.createdAt = createdAt;
		}
	}

	@Nested
	@DisplayName("Test plan creation with invalid params")
	class TestPlanCreationWithInvalidParams {

		@Test
		@DisplayName("Test creation with null parameter, expected NullPointerException")
		void testNullParam() {
			assertThrows(NullPointerException.class, () -> createTweet(MockData.INVALID_NULL_ID));
			assertThrows(NullPointerException.class, () -> createTweet(MockData.INVALID_NULL_MESSAGE));
			assertThrows(NullPointerException.class, () -> createTweet(MockData.INVALID_NULL_URL));
			assertThrows(NullPointerException.class, () -> createTweet(MockData.INVALID_NULL_REQUESTED_PUBLICATION_DATE));
			assertThrows(NullPointerException.class, () -> createTweet(MockData.INVALID_NULL_PUBLISHED_AT));
			assertThrows(NullPointerException.class, () -> createTweet(MockData.INVALID_NULL_CREATED_AT));
		}
		
		@Test
		@DisplayName("Test creation with domain errors, expected customized domain exceptions")
		void testDomainExceptions() {
			assertThrows(MessageMaxLengthExceededException.class, () -> createTweet(MockData.INVALID_MESSAGE_MAX_LENGTH_EXCEEDED));
		}
	}
	
	  @Nested
	  @DisplayName("Test plan creation")
	  class TestPlanCreation {

	    private Tweet tweet;

	    @BeforeEach
	    void setUp() {
	    	tweet = createTweet(MockData.VALID);
	    }

	    @Test
	    @DisplayName("Test creation, expected not null")
	    void testNotNull() {
	      assertThat(tweet, is(notNullValue()));
	    }

	    @Test
	    @DisplayName("Test creation, expected content")
	    void testEqualsText() {
	      assertThat(tweet.id().id(), is(MockData.VALID.tweetId));
	      assertThat(tweet.message().message(), is(MockData.VALID.message));
	      assertThat(tweet.url().url(), is(MockData.VALID.url));
	      assertThat(tweet.requestedPublicationDate().instant(), is(MockData.VALID.requestedPublicationDate));
	      assertThat(tweet.publishedAt().instant(), is(MockData.VALID.publishedAt));
	      assertThat(tweet.createdAt().instant(), is(MockData.VALID.createdAt));
	    }
	  }
	  
	  @Nested
	  @DisplayName("Test plan for equals and hashcode")
	  class TestPlanEqualsHashCode {

	    private Tweet tweet;

	    @BeforeEach
	    void setUp() {
	    	tweet = createTweet(MockData.VALID);
	    }

	    @Test
	    @DisplayName("Test with itself, expected equals")
	    void testEqualsItself() {
	      assertThat(tweet, is(tweet));
	    }

	    @Test
	    @DisplayName("Test with other object with same id, expected equals and same hashcode")
	    void testSameId() {
	      var sameId = createTweet(MockData.VALID_OTHER_SAME_ID);
	      assertThat(tweet, is(sameId));
	      assertThat(tweet.hashCode(), is(sameId.hashCode()));
	    }

	    @Test
	    @DisplayName("Test with other null, expected not equals")
	    void testNotEqualsNull() {
	      assertNotEquals(null, tweet);
	    }

	    @Test
	    @DisplayName("Test with different id, expected not equals and different hashCode")
	    void testDifferentId() {
	      var other = createTweet(MockData.VALID_OTHER_DIFFERENT_ID);
	      assertThat(tweet, is(not(other)));
	      assertThat(tweet.hashCode(), is(not(other.hashCode())));
	    }
	  }
}
