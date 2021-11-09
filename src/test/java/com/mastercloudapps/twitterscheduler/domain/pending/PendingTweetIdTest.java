package com.mastercloudapps.twitterscheduler.domain.pending;

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

class PendingTweetIdTest {

	private PendingTweetId createPendingTweetId(MockData mockData) {
		return PendingTweetId.valueOf(mockData.id);
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

			assertThrows(NullPointerException.class, () -> createPendingTweetId(MockData.INVALID_ID));
		}
	}

	@Nested
	@DisplayName("Test plan creation")
	class TestPlanCreation {

		private PendingTweetId pendingTweetId;

		@BeforeEach
		void setUp() {

			pendingTweetId = createPendingTweetId(MockData.VALID_ID);
		}

		@Test
		@DisplayName("Test creation, expected not null")
		void testNotNull() {

			assertThat(pendingTweetId, is(notNullValue()));
		}

		@Test
		@DisplayName("Test creation, expected id")
		void testEqualsId() {

			assertThat(pendingTweetId.id(), is(MockData.VALID_ID.id));
		}

		@Test
		@DisplayName("Test toString, expected id contained")
		void testToString() {

			assertThat(pendingTweetId.toString(), containsString(MockData.VALID_ID.id.toString()));
		}		
	}
	
	@Nested
	@DisplayName("Test plan for class default values")
	class TestPlanDefault {
				
		@Test
		@DisplayName("Test defaultId, expected MAX_LONG")
		void testToDefaultId() {

			assertThat(PendingTweetId.defaultValue(), is(Long.MAX_VALUE));
		}
	}

	@Nested
	@DisplayName("Test plan for equals and hashcode")
	class TestPlanEqualsHashCode {

		private PendingTweetId pendingTweetId;

		@BeforeEach
		void setUp() {

			pendingTweetId = createPendingTweetId(MockData.VALID_ID);
		}

		@Test
		@DisplayName("Test with itself, expected equals")
		void testEqualsItself() {

			assertThat(pendingTweetId, is(pendingTweetId));
		}

		@Test
		@DisplayName("Test with same id, expected equals and same hashcode")
		void testSameId() {

			var same = createPendingTweetId(MockData.VALID_ID);
			assertThat(pendingTweetId, is(same));
			assertThat(pendingTweetId.hashCode(), is(same.hashCode()));
		}

		@Test
		@DisplayName("Test with other null, expected not equals")
		void testNotEqualsNull() {

			assertNotEquals(null, pendingTweetId);
		}

		@Test
		@DisplayName("Test with different id, expected not equals and different hashCode")
		void testDifferentId() {

			var other = createPendingTweetId(MockData.VALID_ID_OTHER);
			assertThat(pendingTweetId, is(not(other)));
			assertThat(pendingTweetId.hashCode(), is(not(other.hashCode())));
		}
	}
}
