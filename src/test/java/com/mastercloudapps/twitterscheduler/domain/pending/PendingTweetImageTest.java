package com.mastercloudapps.twitterscheduler.domain.pending;

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

class PendingTweetImageTest {

	private PendingTweetImage createPendingTweetImage(final MockData mockData) {

		return PendingTweetImage.builder()
				.id(mockData.pendingTweetImageId)
				.url(mockData.url)
				.build();
	}

	enum MockData {
		VALID(
				1L,
				"https://mywebpage.eu/images/tfm/1.jpg"),
		VALID_OTHER_DIFFERENT_ID(
				2L, 
				"https://mywebpage.eu/images/tfm/3.jpg"),
		INVALID_NULL_ID(
				null, 
				"https://mywebpage.eu/images/tfm/1.jpg"),
		INVALID_NULL_URL(
				1L,
				null);

		private final Long pendingTweetImageId;

		private final String url;

		MockData(final Long pendingTweetImageId, final String url) {
			this.pendingTweetImageId = pendingTweetImageId;
			this.url = url;
		}
	}

	@Nested
	@DisplayName("Test plan creation with invalid params")
	class TestPlanCreationWithInvalidParams {

		@Test
		@DisplayName("Test creation with null parameter, expected NullPointerException")
		void testNullParam() {
			assertThrows(NullPointerException.class, () -> createPendingTweetImage(MockData.INVALID_NULL_ID));
			assertThrows(NullPointerException.class, () -> createPendingTweetImage(MockData.INVALID_NULL_URL));
		}
	}

	@Nested
	@DisplayName("Test plan creation with valid params")
	class TestPlanCreationWithValidParams {

		private PendingTweetImage pendingTweetImage;

		@BeforeEach
		void setUp() {
			pendingTweetImage = createPendingTweetImage(MockData.VALID);
		}

		@Test
		@DisplayName("Test creation, expected not null")
		void testNotNull() {
			assertThat(pendingTweetImage, is(notNullValue()));
		}

		@Test
		@DisplayName("Test creation, expected content")
		void testEqualsText() {
			assertThat(pendingTweetImage.id().id(), is(MockData.VALID.pendingTweetImageId));
			assertThat(pendingTweetImage.url().url(), is(MockData.VALID.url));
		}
	}

	@Nested
	@DisplayName("Test plan for equals and hashcode")
	class TestPlanEqualsHashCode {

		private PendingTweetImage pendingTweetImage;

		@BeforeEach
		void setUp() {
			pendingTweetImage = createPendingTweetImage(MockData.VALID);
		}

		@Test
		@DisplayName("Test with itself, expected equals")
		void testEqualsItself() {
			assertThat(pendingTweetImage, is(pendingTweetImage));
		}

		@Test
		@DisplayName("Test with other null, expected not equals")
		void testNotEqualsNull() {
			assertNotEquals(null, pendingTweetImage);
		}

		@Test
		@DisplayName("Test with different id, expected not equals and different hashCode")
		void testDifferentId() {
			var other = createPendingTweetImage(MockData.VALID_OTHER_DIFFERENT_ID);
			assertThat(pendingTweetImage, is(not(other)));
			assertThat(pendingTweetImage.hashCode(), is(not(other.hashCode())));
		}
	}
}
