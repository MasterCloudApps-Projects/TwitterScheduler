package com.mastercloudapps.twitterscheduler.controller.pending.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.togglz.core.manager.FeatureManager;

import com.mastercloudapps.twitterscheduler.configuration.featureflags.Features;
import com.mastercloudapps.twitterscheduler.mocks.PendingTweetData;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PendingTweetResponseMapperTest {
	
	private PendingTweetResponseMapper mapper;
	
	@Mock
	private FeatureManager featureManager;
	
	@BeforeEach
	void setUp() {
		mapper = new PendingTweetResponseMapper(featureManager);
	}

	@Nested
	@DisplayName("Test plan valid params")
	class TestPlanValidParams {

		@Test
		void mapAllAttributesIsOk() {

			when(featureManager.isActive(Features.TWEETS_WITH_IMAGES)).thenReturn(false);
			
			final var pendingTweet = PendingTweetData.MERRY_CHRISTMAS.create();
			final var response = mapper.mapResponse(pendingTweet);

			assertThat(response, is(notNullValue()));
			assertThat(response.getId(), is(pendingTweet.id().id()));
			assertThat(response.getMessage(), is(pendingTweet.message().message()));
//			assertThat(response.getPublicationDate(), is(pendingTweet.publicationDate().instant()));
//			assertThat(response.getCreatedAt(), is(pendingTweet.createdAt().instant()));
		}
	}
}