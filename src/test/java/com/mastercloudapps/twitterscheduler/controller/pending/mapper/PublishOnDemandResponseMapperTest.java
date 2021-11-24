package com.mastercloudapps.twitterscheduler.controller.pending.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.mastercloudapps.twitterscheduler.mocks.TweetData;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PublishOnDemandResponseMapperTest {

private PublishOnDemandResponseMapper mapper;

	@BeforeEach
	void setUp() {
		mapper = new PublishOnDemandResponseMapper();
	}

	@Nested
	@DisplayName("Test plan valid params")
	class TestPlanValidParams {

		@Test
		void mapAllAttributesIsOk() {
			final var tweet = TweetData.MERRY_CHRISTMAS.create();
			final var response = mapper.mapResponse(tweet);

			assertThat(response, is(notNullValue()));
			assertThat(response.getTweetId(), is(tweet.id().id()));
		}
	}
}