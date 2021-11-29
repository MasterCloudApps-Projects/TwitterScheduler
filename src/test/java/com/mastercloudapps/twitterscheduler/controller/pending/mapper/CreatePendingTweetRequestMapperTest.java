package com.mastercloudapps.twitterscheduler.controller.pending.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Assertions;
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
import com.mastercloudapps.twitterscheduler.controller.exception.ExpiredPublicationDateException;
import com.mastercloudapps.twitterscheduler.controller.exception.InvalidInputException;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingTweetRequest;
import com.mastercloudapps.twitterscheduler.controller.validator.ImageAvailable;
import com.mastercloudapps.twitterscheduler.domain.shared.NullableInstant;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CreatePendingTweetRequestMapperTest {

	private CreatePendingTweetRequestMapper mapper;

	@Mock
	private ImageAvailable imageAvailableValidator;
	
	@Mock
	private FeatureManager featureManager;
	
	@BeforeEach
	void setUp() {
		mapper = new CreatePendingTweetRequestMapper(featureManager, imageAvailableValidator);
	}

	private PendingTweetRequest buildRequest(MockData mockData) {

		final var builder = PendingTweetRequest
				.builder()
				.message(mockData.message)
				.publicationDate(mockData.publicationDate);

		return builder.build();
	}

	enum MockData {
		VALID_REQUEST("test message 1", "2022-04-01T10:00:00Z"),
		REQUEST_WITHOUT_MESSAGE("", "2022-05-01T10:00:00Z"),
		REQUEST_WITHOUT_PUBLICATION_DATE("test message 2", ""),
		REQUEST_WITH_EXPIRED_PUBLICATION_DATE("test message 2", "2021-05-01T10:00:00Z"),
		REQUEST_WITH_INVALID_DATE("test message 2", "1");

		private final String message;

		private final String publicationDate;

		MockData(final String message, final String publicationDate) {
			this.message = message;
			this.publicationDate = publicationDate;
		}
	}

	@Nested
	@DisplayName("Test plan invalid params")
	class TestPlanInvalidParams {

		@Test
		void mapQueryRequest_whenNull_shouldReturnException() {

			Assertions.assertThrows(InvalidInputException.class, () -> {
				mapper.mapRequest(null);
			});
		}

		@Test
		void mapQueryRequest_whenMessageNull_shouldReturnException() {

			assertMapperThrowsException(InvalidInputException.class, MockData.REQUEST_WITHOUT_MESSAGE);
		}

		@Test
		void mapQueryRequest_whenPublicationDateNull_shouldReturnException() {

			assertMapperThrowsException(InvalidInputException.class, MockData.REQUEST_WITHOUT_PUBLICATION_DATE);
		}

		@Test
		void mapQueryRequest_withInvalidDate_shouldReturnException() {

			assertMapperThrowsException(DateTimeParseException.class, MockData.REQUEST_WITH_INVALID_DATE);
		}
		
		@Test
		void mapQueryRequest_withExpiredDate_shouldReturnException() {

			assertMapperThrowsException(ExpiredPublicationDateException.class, MockData.REQUEST_WITH_EXPIRED_PUBLICATION_DATE);
		}
	}

	@Nested
	@DisplayName("Test plan valid params")
	class TestPlanValidParams {

		@Test
		void mapAllAttributesIsOk() {

			when(featureManager.isActive(Features.TWEETS_WITH_IMAGES)).thenReturn(false);
			
			PendingTweetRequest request = buildRequest(MockData.VALID_REQUEST);
			final var createPendingTweetOperation = mapper.mapRequest(request);

			assertThat(createPendingTweetOperation, is(notNullValue()));
			assertThat(createPendingTweetOperation.getMessage(), is(MockData.VALID_REQUEST.message));
			assertThat(createPendingTweetOperation.getPublicationDate(), is(NullableInstant.fromUtcISO8601(MockData.VALID_REQUEST.publicationDate)));
		}
	}

	private void assertMapperThrowsException(Class<? extends Exception> ex, MockData requestType) {

		final var request = buildRequest(requestType);
		Assertions.assertThrows(ex, () -> {
			mapper.mapRequest(request);
		});
	}
}