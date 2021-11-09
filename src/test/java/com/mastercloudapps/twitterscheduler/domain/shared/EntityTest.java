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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.mastercloudapps.twitterscheduler.domain.shared.id.DomainObjectId;

@Tag("shared-domain")
class EntityTest {

	private Entity<DomainObjectId> getMockEntity(DomainObjectId id) {

		return new Entity<>(id) {

			private static final long serialVersionUID = -7723253451811416419L;	      
		};
	}

	private DomainObjectId getMockDomainObjectId(MockData mockData) {

		return new DomainObjectId(mockData.id);
	}

	enum MockData {
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
		@DisplayName("Test creation with null ID, expected NullPointerException")
		void testNullUuid() {

			assertThrows(NullPointerException.class, () -> getMockEntity(null));
		}
	}

	@Nested
	@DisplayName("Test plan for creation with valid params")
	class TestPlanCreationWithValidParams {

		private DomainObjectId expectedDomainObjectId;

		private Entity<DomainObjectId> entity;

		@BeforeEach
		void setUp() {

			expectedDomainObjectId = getMockDomainObjectId(MockData.VALID_ID);
			entity = getMockEntity(expectedDomainObjectId);
		}

		@Test
		@DisplayName("Test creation, expected not null")
		void testNotNull() {

			assertThat(entity, is(notNullValue()));
		}

		@Test
		@DisplayName("Test creation, expected ID")
		void testEqualsId() {

			assertThat(entity.id(), is(expectedDomainObjectId));
		}

		@Test
		@DisplayName("Test toString, expected id contained")
		void testToString() {

			assertThat(entity.toString(), containsString(MockData.VALID_ID.id));
		}
	}

	@Nested
	@DisplayName("Test plan for equals and hashCode")
	class TestPlanEquals {

		private Entity<DomainObjectId> entity;

		@BeforeEach
		void setUp() {

			final var expectedDomainObjectId = getMockDomainObjectId(MockData.VALID_ID);
			entity = getMockEntity(expectedDomainObjectId);
		}

		@Test
		@DisplayName("Test with itself, expected equals and same hashCode")
		void testEqualsItself() {

			assertThat(entity, is(entity));
			assertThat(entity.hashCode(), is(entity.hashCode()));
		}

		@Test
		@DisplayName("Test with same object, expected equals and same hashcode")
		void testEqualsSame() {

			var same = getMockEntity(getMockDomainObjectId(MockData.VALID_ID));
			assertThat(entity, is(same));
		}

		@Test
		@DisplayName("Test with other null, expected not equals")
		void testNotEqualsNull() {

			assertNotEquals(null, entity);
		}

		@Test
		@DisplayName("Test with different object, expected not equals  and different hashCode")
		void testNotEqualsDifferent() {

			var other = getMockEntity(getMockDomainObjectId(MockData.VALID_ID_OTHER));
			assertThat(entity, is(not(other)));
			assertThat(entity.hashCode(), is(not(other.hashCode())));
		}
	}
}
