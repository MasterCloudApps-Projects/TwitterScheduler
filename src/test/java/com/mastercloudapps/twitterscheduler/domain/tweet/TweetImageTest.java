package com.mastercloudapps.twitterscheduler.domain.tweet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TweetImageTest {

	private TweetImage createTweetImage(final MockData mockData) {

		return TweetImage.builder()
				.id(mockData.tweetImageId)
				.size(mockData.size)
				.type(mockData.type)
				.width(mockData.width)
				.height(mockData.height)
				.build();
	}
	
	enum MockData {
		VALID(
				1L,
				300L,
				"image/jpeg",
				100,
				100),
		VALID_OTHER_DIFFERENT_ID(
				2L,
				400L,
				"image/png",
				200,
				200),
		INVALID_NULL_ID(
				null, 
				400L,
				"image/png",
				200,
				200),
		INVALID_NULL_SIZE(
				3L,
				null,
				"image/jpeg",
				100,
				100),
		INVALID_NULL_TYPE(
				4L,
				200L,
				null,
				100,
				100),
		INVALID_NULL_WIDTH(
				5L,
				180L,
				"image/jpeg",
				null,
				100),
		INVALID_NULL_HEIGHT(
				6L,
				389L,
				"image/jpeg",
				100,
				null);

		private final Long tweetImageId;

		private final Long size;
		
		private final String type;
		
		private final Integer width;
		
		private final Integer height;

		MockData(final Long tweetImageId, final Long size, final String type, final Integer width,
				final Integer height) {
			this.tweetImageId = tweetImageId;
			this.size = size;
			this.type = type;
			this.width = width;
			this.height = height;
		}
	}
	
	@Nested
	@DisplayName("Test plan creation with invalid params")
	class TestPlanCreationWithInvalidParams {

		@Test
		@DisplayName("Test creation with null parameter, expected NullPointerException")
		void testNullParam() {
			assertThrows(NullPointerException.class, () -> createTweetImage(MockData.INVALID_NULL_ID));
			assertThrows(NullPointerException.class, () -> createTweetImage(MockData.INVALID_NULL_SIZE));
			assertThrows(NullPointerException.class, () -> createTweetImage(MockData.INVALID_NULL_TYPE));
			assertThrows(NullPointerException.class, () -> createTweetImage(MockData.INVALID_NULL_WIDTH));
			assertThrows(NullPointerException.class, () -> createTweetImage(MockData.INVALID_NULL_HEIGHT));
		}
	}
	
	@Nested
	@DisplayName("Test plan creation with valid params")
	class TestPlanCreationWithValidParams {

		private TweetImage tweetImage;

		@BeforeEach
		void setUp() {
			tweetImage = createTweetImage(MockData.VALID);
		}

		@Test
		@DisplayName("Test creation, expected not null")
		void testNotNull() {
			assertThat(tweetImage, is(notNullValue()));
		}

		@Test
		@DisplayName("Test creation, expected content")
		void testEqualsText() {
			assertThat(tweetImage.id().id(), is(MockData.VALID.tweetImageId));
			assertThat(tweetImage.size().size(), is(MockData.VALID.size));
			assertThat(tweetImage.type().type(), is(MockData.VALID.type));
			assertThat(tweetImage.width().width(), is(MockData.VALID.width));
			assertThat(tweetImage.height().height(), is(MockData.VALID.height));
		}
	}
	
	@Nested
	@DisplayName("Test plan for equals and hashcode")
	class TestPlanEqualsHashCode {

		private TweetImage tweetImage;

		@BeforeEach
		void setUp() {
			tweetImage = createTweetImage(MockData.VALID);
		}

		@Test
		@DisplayName("Test with itself, expected equals")
		void testEqualsItself() {
			assertThat(tweetImage, is(tweetImage));
		}

		@Test
		@DisplayName("Test with other null, expected not equals")
		void testNotEqualsNull() {
			assertNotEquals(null, tweetImage);
		}

		@Test
		@DisplayName("Test with different id, expected not equals and different hashCode")
		void testDifferentId() {
			var other = createTweetImage(MockData.VALID_OTHER_DIFFERENT_ID);
			assertThat(tweetImage, is(not(other)));
			assertThat(tweetImage.hashCode(), is(not(other.hashCode())));
		}
	}
}
