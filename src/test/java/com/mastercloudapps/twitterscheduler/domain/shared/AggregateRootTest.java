package com.mastercloudapps.twitterscheduler.domain.shared;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.mastercloudapps.twitterscheduler.domain.shared.id.DomainObjectId;

@Tag("shared-domain")
class AggregateRootTest {

	private AggregateRoot<DomainObjectId> getMockAggregateRoot(DomainObjectId id) {

		return new AggregateRoot<>(id) {
		};
	}

	private DomainObjectId getMockDomainObjectId(MockData mockData) {

		return new DomainObjectId(mockData.id);
	}

	enum MockData {
		VALID_DATA("0", Instant.ofEpochSecond(1550000001L));

		private final String id;

		private final Instant instant;

		MockData(String id, Instant instant) {

			this.id = id;
			this.instant = instant;
		}
	}

	@Nested
	@DisplayName("Test plan creation with invalid params")
	class TestPlanCreationWithInvalidParams {

		@Test
		@DisplayName("Test creation with null ID, expected NullPointerException")
		void testNullUuid() {

			assertThrows(NullPointerException.class, () -> getMockAggregateRoot(null));
		}
	}

	@Nested
	@DisplayName("Test plan creation with valid params")
	class TestPlanCreationWithValidParams {

		private DomainObjectId expectedId;

		private AggregateRoot<DomainObjectId> myAggregateRoot;

		@BeforeEach
		void setUp() {

			expectedId = getMockDomainObjectId(MockData.VALID_DATA);
			myAggregateRoot = getMockAggregateRoot(expectedId);
		}

		@Test
		@DisplayName("Test creation, expected not null")
		void testNotNull() {

			assertThat(myAggregateRoot, is(notNullValue()));
		}

		@Test
		@DisplayName("Test creation, expected ID")
		void testEqualsId() {

			assertThat(myAggregateRoot.id(), is(expectedId));
		}

		@Test
		@DisplayName("Test creation, expected value for updatedAt")
		void testNotNullUpdatedAt() {

			assertThat(myAggregateRoot.updatedAt(), is(notNullValue()));
			assertThat(myAggregateRoot.updatedAt().instant(), is(notNullValue()));
		}

	}
}
