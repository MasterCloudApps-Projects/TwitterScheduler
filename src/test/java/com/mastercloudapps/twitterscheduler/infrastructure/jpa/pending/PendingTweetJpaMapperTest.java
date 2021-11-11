package com.mastercloudapps.twitterscheduler.infrastructure.jpa.pending;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.mastercloudapps.twitterscheduler.infrastructure.jpa.pending.PendingTweetJpaMapper;
import com.mastercloudapps.twitterscheduler.mocks.PendingTweetData;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PendingTweetJpaMapperTest {
	
	private PendingTweetJpaMapper mapper;
	
	@BeforeEach
	void setUp() {
		mapper = new PendingTweetJpaMapper();
	}
	
	@Nested
	@DisplayName("Test plan valid params")
	class TestPlanValidParams {

		@Test
		void mapAllAttributesIsOk() {
			final var expectedResult = PendingTweetData.MERRY_CHRISTMAS.create();
			final var jpaEntity = mapper.mapDomainObject(expectedResult);

			assertThat(jpaEntity, is(notNullValue()));
			assertThat(jpaEntity.getId(), is(expectedResult.id().id()));
			assertThat(jpaEntity.getMessage(), is(expectedResult.message().message()));
			assertThat(jpaEntity.getPublicationDate(), is(expectedResult.publicationDate().instant()));
		}
	}
	

}
