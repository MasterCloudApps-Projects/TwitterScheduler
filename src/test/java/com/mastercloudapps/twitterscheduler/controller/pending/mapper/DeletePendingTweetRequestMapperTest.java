package com.mastercloudapps.twitterscheduler.controller.pending.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.mastercloudapps.twitterscheduler.application.model.operation.DeletePendingTweetOperation;
import com.mastercloudapps.twitterscheduler.controller.exception.InvalidInputException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeletePendingTweetRequestMapperTest {

	private DeletePendingTweetRequestMapper mapper;

	@BeforeEach
	void setUp() {
		mapper = new DeletePendingTweetRequestMapper();
	}

	private DeletePendingTweetOperation buildRequest(MockData mockData) {

		final var builder = DeletePendingTweetOperation
				.builder()
				.id(mockData.id);

		return builder.build();
	}

	enum MockData {
		VALID_REQUEST(1L);

		private final Long id;

		MockData(final Long id) {
			this.id = id;
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
	}

	@Nested
	@DisplayName("Test plan valid params")
	class TestPlanValidParams {

		@Test
		void mapAllAttributesIsOk() {

			final var deletePendingTweetOperation = mapper.mapRequest(buildRequest(MockData.VALID_REQUEST).getId());

			assertThat(deletePendingTweetOperation, is(notNullValue()));
			assertThat(deletePendingTweetOperation.getId(), is(MockData.VALID_REQUEST.id));
		}
	}
}
