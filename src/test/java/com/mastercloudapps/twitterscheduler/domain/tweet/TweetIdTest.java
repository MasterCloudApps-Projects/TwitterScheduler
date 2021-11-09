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

class TweetIdTest {

	private TweetId createTweetId(MockData mockData) {
		return TweetId.valueOf(mockData.id);
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

			assertThrows(NullPointerException.class, () -> createTweetId(MockData.INVALID_ID));
		}
	}

	@Nested
	@DisplayName("Test plan creation")
	class TestPlanCreation {

		private TweetId tweetId;

		@BeforeEach
		void setUp() {

			tweetId = createTweetId(MockData.VALID_ID);
		}

		@Test
		@DisplayName("Test creation, expected not null")
		void testNotNull() {

			assertThat(tweetId, is(notNullValue()));
		}

		@Test
		@DisplayName("Test creation, expected id")
		void testEqualsId() {

			assertThat(tweetId.id(), is(MockData.VALID_ID.id));
		}

		@Test
		@DisplayName("Test toString, expected id contained")
		void testToString() {

			assertThat(tweetId.toString(), containsString(MockData.VALID_ID.id.toString()));
		}		
	}
	

	@Nested
	@DisplayName("Test plan for equals and hashcode")
	class TestPlanEqualsHashCode {

		private TweetId tweetId;

		@BeforeEach
		void setUp() {

			tweetId = createTweetId(MockData.VALID_ID);
		}

		@Test
		@DisplayName("Test with itself, expected equals")
		void testEqualsItself() {

			assertThat(tweetId, is(tweetId));
		}

		@Test
		@DisplayName("Test with same id, expected equals and same hashcode")
		void testSameId() {

			var same = createTweetId(MockData.VALID_ID);
			assertThat(tweetId, is(same));
			assertThat(tweetId.hashCode(), is(same.hashCode()));
		}

		@Test
		@DisplayName("Test with other null, expected not equals")
		void testNotEqualsNull() {

			assertNotEquals(null, tweetId);
		}

		@Test
		@DisplayName("Test with different id, expected not equals and different hashCode")
		void testDifferentId() {

			var other = createTweetId(MockData.VALID_ID_OTHER);
			assertThat(tweetId, is(not(other)));
			assertThat(tweetId.hashCode(), is(not(other.hashCode())));
		}
	}
}
