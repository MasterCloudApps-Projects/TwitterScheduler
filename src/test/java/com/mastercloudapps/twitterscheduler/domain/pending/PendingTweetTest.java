package com.mastercloudapps.twitterscheduler.domain.pending;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.mastercloudapps.twitterscheduler.domain.exception.MessageMaxLengthExceededException;
import com.mastercloudapps.twitterscheduler.domain.shared.NullableInstant;

class PendingTweetTest {

	private PendingTweet createPendingTweet(final MockData mockData) {

		return PendingTweet.builder()
				.id(mockData.pendingTweetId)
				.message(mockData.message)
				.publicationDate(mockData.publicationDate)
				.createdAt(mockData.createdAt)
				.build();
	}

	enum MockData {
		VALID(
				1L,
				"valid message 1",
				LocalDateTime.of(2030, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
				NullableInstant.now().instant()),
		VALID_OTHER_SAME_ID(
				1L, 
				"valid message 2",
				LocalDateTime.of(2031, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
				NullableInstant.now().instant()),
		VALID_OTHER_DIFFERENT_ID(
				2L, 
				"valid message 3", 
				LocalDateTime.of(2032, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
				NullableInstant.now().instant()),
		INVALID_NULL_ID(
				null, 
				"abc",
				Instant.MAX,
				NullableInstant.now().instant()),
		INVALID_NULL_MESSAGE(
				1L,
				null,
				Instant.MAX,
				NullableInstant.now().instant()),
		INVALID_NULL_PUBLICATION_DATE(
				1L,
				"xyz",
				null,
				NullableInstant.now().instant()),
		INVALID_NULL_CREATED_AT(
				1L,
				"ijk",
				Instant.MAX,
				null),
		INVALID_MESSAGE_MAX_LENGTH_EXCEEDED(
				1L,
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the "
						+ "industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type "
						+ "and scrambled it to make a type specimen book. It has survived not only five years.",
				LocalDateTime.of(2033, 1, 1, 0, 0).toInstant(ZoneOffset.UTC),
				NullableInstant.now().instant());

		private final Long pendingTweetId;

		private final String message;

		private final Instant publicationDate;
		
		private final Instant createdAt;

		MockData(final Long pendingTweetId, final String message, final Instant publicationDate, final Instant createdAt) {
			this.pendingTweetId = pendingTweetId;
			this.message = message;
			this.publicationDate = publicationDate;
			this.createdAt = createdAt;
		}
	}

	@Nested
	@DisplayName("Test plan creation with invalid params")
	class TestPlanCreationWithInvalidParams {

		@Test
		@DisplayName("Test creation with null parameter, expected NullPointerException")
		void testNullParam() {
			assertThrows(NullPointerException.class, () -> createPendingTweet(MockData.INVALID_NULL_ID));
			assertThrows(NullPointerException.class, () -> createPendingTweet(MockData.INVALID_NULL_MESSAGE));
			assertThrows(NullPointerException.class, () -> createPendingTweet(MockData.INVALID_NULL_PUBLICATION_DATE));
			assertThrows(NullPointerException.class, () -> createPendingTweet(MockData.INVALID_NULL_CREATED_AT));
		}
		
		@Test
		@DisplayName("Test creation with domain errors, expected customized domain exceptions")
		void testDomainExceptions() {
			assertThrows(MessageMaxLengthExceededException.class, () -> createPendingTweet(MockData.INVALID_MESSAGE_MAX_LENGTH_EXCEEDED));
		}
	}
	
	  @Nested
	  @DisplayName("Test plan creation")
	  class TestPlanCreation {

	    private PendingTweet pendingTweet;

	    @BeforeEach
	    void setUp() {
	    	pendingTweet = createPendingTweet(MockData.VALID);
	    }

	    @Test
	    @DisplayName("Test creation, expected not null")
	    void testNotNull() {
	      assertThat(pendingTweet, is(notNullValue()));
	    }

	    @Test
	    @DisplayName("Test creation, expected content")
	    void testEqualsText() {
	      assertThat(pendingTweet.id().id(), is(MockData.VALID.pendingTweetId));
	      assertThat(pendingTweet.message().message(), is(MockData.VALID.message));
	      assertThat(pendingTweet.publicationDate().instant(), is(MockData.VALID.publicationDate));
	      assertThat(pendingTweet.createdAt().instant(), is(MockData.VALID.createdAt));
	    }
	  }
	  
	  @Nested
	  @DisplayName("Test plan for equals and hashcode")
	  class TestPlanEqualsHashCode {

	    private PendingTweet pendingTweet;

	    @BeforeEach
	    void setUp() {
	    	pendingTweet = createPendingTweet(MockData.VALID);
	    }

	    @Test
	    @DisplayName("Test with itself, expected equals")
	    void testEqualsItself() {
	      assertThat(pendingTweet, is(pendingTweet));
	    }

	    @Test
	    @DisplayName("Test with other object with same id, expected equals and same hashcode")
	    void testSameId() {
	      var sameId = createPendingTweet(MockData.VALID_OTHER_SAME_ID);
	      assertThat(pendingTweet, is(sameId));
	      assertThat(pendingTweet.hashCode(), is(sameId.hashCode()));
	    }

	    @Test
	    @DisplayName("Test with other null, expected not equals")
	    void testNotEqualsNull() {
	      assertNotEquals(null, pendingTweet);
	    }

	    @Test
	    @DisplayName("Test with different id, expected not equals and different hashCode")
	    void testDifferentId() {
	      var other = createPendingTweet(MockData.VALID_OTHER_DIFFERENT_ID);
	      assertThat(pendingTweet, is(not(other)));
	      assertThat(pendingTweet.hashCode(), is(not(other.hashCode())));
	    }
	  }
	  
	  @Nested
	  @DisplayName("Test images list")
	  class TestPlanAssociationsList {

		  private PendingTweet pendingTweet;

		  private PendingTweetImage image;

		  @BeforeEach
		  void setUp() {
			  pendingTweet = createPendingTweet(MockData.VALID);
			  image = PendingTweetImage.builder()
					  .id(1L)
					  .url("abc")
					  .build();
		  }

		  @Test
		  @DisplayName("Add image, expect contained in images list.")
		  void testAddImage() {
			  pendingTweet.addImages(image);
			  assertThat(pendingTweet.getImages().contains(image), is(true));
		  }

		  @Test
		  @DisplayName("Add image list, expect contained in images list.")
		  void testAddImagesList() {
			  pendingTweet.addImages(List.of(image));
			  assertThat(pendingTweet.getImages().contains(image), is(true));
		  }

		  @Test
		  @DisplayName("Remove all images, expect images empty.")
		  void testRemoveAllImages() {
			  pendingTweet.addImages(image);
			  assertThat(pendingTweet.getImages(), is(not(empty())));
			  pendingTweet.deleteImages();
			  assertThat(pendingTweet.getImages(), is(empty()));
		  }

		  @Test
		  @DisplayName("Remove an image, expect not present in images list.")
		  void testRemoveImage() {
			  pendingTweet.addImages(image);
			  assertThat(pendingTweet.getImages(), is(not(empty())));
			  pendingTweet.deleteImages(image);
			  assertThat(pendingTweet.getImages().contains(image), is(false));
		  }
	  }
}
