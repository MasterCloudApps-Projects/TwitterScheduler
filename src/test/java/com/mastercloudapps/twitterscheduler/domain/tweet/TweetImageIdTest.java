package com.mastercloudapps.twitterscheduler.domain.tweet;

import static org.hamcrest.CoreMatchers.containsString;
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

class TweetImageIdTest {

	private TweetImageId createTweetImageId(MockData mockData) {
		return TweetImageId.valueOf(mockData.id);
	}

	enum MockData {
		INVALID_ID(null),
		VALID_ID(1L),
		VALID_ID_OTHER(2L);

		private final Long id;

		MockData(Long id) {
			this.id = id;
		}
	}

	@Nested
	@DisplayName("Test plan creation with invalid params")
	class TestPlanCreationWithInvalidParams {

		@Test
		@DisplayName("Test creation with null id, expected NullPointerException")
		void testNullId() {

			assertThrows(NullPointerException.class, () -> createTweetImageId(MockData.INVALID_ID));
		}
	}

	@Nested
	@DisplayName("Test plan creation")
	class TestPlanCreation {

		private TweetImageId tweetImageId;

		@BeforeEach
		void setUp() {

			tweetImageId = createTweetImageId(MockData.VALID_ID);
		}

		@Test
		@DisplayName("Test creation, expected not null")
		void testNotNull() {

			assertThat(tweetImageId, is(notNullValue()));
		}

		@Test
		@DisplayName("Test creation, expected id")
		void testEqualsId() {

			assertThat(tweetImageId.id(), is(MockData.VALID_ID.id));
		}

		@Test
		@DisplayName("Test toString, expected id contained")
		void testToString() {

			assertThat(tweetImageId.toString(), containsString(MockData.VALID_ID.id.toString()));
		}		
	}

	@Nested
	@DisplayName("Test plan for equals and hashcode")
	class TestPlanEqualsHashCode {

		private TweetImageId tweetImageId;

		@BeforeEach
		void setUp() {

			tweetImageId = createTweetImageId(MockData.VALID_ID);
		}

		@Test
		@DisplayName("Test with itself, expected equals")
		void testEqualsItself() {

			assertThat(tweetImageId, is(tweetImageId));
		}

		@Test
		@DisplayName("Test with same id, expected equals and same hashcode")
		void testSameId() {

			var same = createTweetImageId(MockData.VALID_ID);
			assertThat(tweetImageId, is(same));
			assertThat(tweetImageId.hashCode(), is(same.hashCode()));
		}

		@Test
		@DisplayName("Test with other null, expected not equals")
		void testNotEqualsNull() {

			assertNotEquals(null, tweetImageId);
		}

		@Test
		@DisplayName("Test with different id, expected not equals and different hashCode")
		void testDifferentId() {

			var other = createTweetImageId(MockData.VALID_ID_OTHER);
			assertThat(tweetImageId, is(not(other)));
			assertThat(tweetImageId.hashCode(), is(not(other.hashCode())));
		}
	}
}
