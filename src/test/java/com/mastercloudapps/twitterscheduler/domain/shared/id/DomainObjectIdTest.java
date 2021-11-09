package com.mastercloudapps.twitterscheduler.domain.shared.id;

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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("shared-domain")
class DomainObjectIdTest {

	private DomainObjectId getMockDomainObjectId(MockData mockData) {
		return new DomainObjectId(mockData.id);
	}

	enum MockData {
		INVALID_ID(null),
		VALID_ID("0"),
		VALID_ID_OTHER("1");

		private final String id;

		MockData(String id) {
			this.id = id;
		}
	}

	@Nested
	@DisplayName("Test plan creation with invalid params")
	class TestPlanCreationWithInvalidParams {

		@Test
		@DisplayName("Test creation with null id, expected NullPointerException")
		void testNullId() {

			assertThrows(NullPointerException.class, () -> getMockDomainObjectId(MockData.INVALID_ID));
		}
	}

	@Nested
	@DisplayName("Test plan creation with custom id")
	class TestPlanCreationWithCustomId {

		private DomainObjectId domainObjectId;

		@BeforeEach
		void setUp() {

			domainObjectId = getMockDomainObjectId(MockData.VALID_ID);
		}

		@Test
		@DisplayName("Test creation, expected not null")
		void testNotNull() {

			assertThat(domainObjectId, is(notNullValue()));
		}

		@Test
		@DisplayName("Test creation, expected id")
		void testEqualsId() {

			assertThat(domainObjectId.id(), is(MockData.VALID_ID.id));
		}

		@Test
		@DisplayName("Test toString, expected id contained")
		void testToString() {

			assertThat(domainObjectId.toString(), containsString(MockData.VALID_ID.id));
		}
	}

	@Nested
	@DisplayName("Test plan for equals and hashcode")
	class TestPlanEqualsHashCode {

		private DomainObjectId domainObjectId;

		@BeforeEach
		void setUp() {

			domainObjectId = getMockDomainObjectId(MockData.VALID_ID);
		}

		@Test
		@DisplayName("Test with itself, expected equals")
		void testEqualsItself() {

			assertThat(domainObjectId, is(domainObjectId));
		}

		@Test
		@DisplayName("Test with same content, expected equals and same hashcode")
		void testSameContent() {

			var same = getMockDomainObjectId(MockData.VALID_ID);
			assertThat(domainObjectId, is(same));
			assertThat(domainObjectId.hashCode(), is(same.hashCode()));
		}

		@Test
		@DisplayName("Test with other null, expected not equals")
		void testNotEqualsNull() {

			assertNotEquals(null, domainObjectId);
		}

		@Test
		@DisplayName("Test with different content, expected not equals and different hashCode")
		void testDifferentContent() {

			var other = getMockDomainObjectId(MockData.VALID_ID_OTHER);
			assertThat(domainObjectId, is(not(other)));
			assertThat(domainObjectId.hashCode(), is(not(other.hashCode())));
		}
	}
}
