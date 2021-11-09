package com.mastercloudapps.twitterscheduler.domain.shared;

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

class MessageTest {

	private Message createMessage(MockData mockData) {
		return Message.valueOf(mockData.message);
	}

	enum MockData {
		INVALID_TITLE(null),
		VALID_MESSAGE("MESSAGE_0"),
		VALID_MESSAGE_OTHER("MESSAGE_1");

		private final String message;

		MockData(String message) {
			this.message = message;
		}
	}

	@Nested
	@DisplayName("Test plan creation with invalid params")
	class TestPlanCreationWithInvalidParams {

		@Test
		@DisplayName("Test creation with null parameter, expected NullPointerException")
		void testNullParam() {

			assertThrows(NullPointerException.class, () -> createMessage(MockData.INVALID_TITLE));
		}
	}

	@Nested
	@DisplayName("Test plan creation")
	class TestPlanCreation {

		private Message message;

		@BeforeEach
		void setUp() {

			message = createMessage(MockData.VALID_MESSAGE);
		}

		@Test
		@DisplayName("Test creation, expected not null")
		void testNotNull() {

			assertThat(message, is(notNullValue()));
		}

		@Test
		@DisplayName("Test creation, expected title")
		void testEqualsMessage() {

			assertThat(message.message(), is(MockData.VALID_MESSAGE.message));
		}

		@Test
		@DisplayName("Test toString, expected title contained")
		void testToString() {

			assertThat(message.toString(), containsString(MockData.VALID_MESSAGE.message));
		}
	}

	@Nested
	@DisplayName("Test plan for equals and hashcode")
	class TestPlanEqualsHashCode {

		private Message message;

		@BeforeEach
		void setUp() {

			message = createMessage(MockData.VALID_MESSAGE);
		}

		@Test
		@DisplayName("Test with itself, expected equals")
		void testEqualsItself() {

			assertThat(message, is(message));
		}

		@Test
		@DisplayName("Test with other object with same attributes, expected equals and same hashcode")
		void testSameContent() {

			var same = createMessage(MockData.VALID_MESSAGE);
			assertThat(message, is(same));
			assertThat(message.hashCode(), is(same.hashCode()));
		}

		@Test
		@DisplayName("Test with other null, expected not equals")
		void testNotEqualsNull() {

			assertNotEquals(null, message);
		}

		@Test
		@DisplayName("Test with different content, expected not equals and different hashCode")
		void testDifferentContent() {

			var other = createMessage(MockData.VALID_MESSAGE_OTHER);
			assertThat(message, is(not(other)));
			assertThat(message.hashCode(), is(not(other.hashCode())));
		}
	}
}
