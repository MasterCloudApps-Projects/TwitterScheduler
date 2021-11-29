package com.mastercloudapps.twitterscheduler.controller.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageAvailableTest {

	private ImageAvailable validator;

	@BeforeEach
	public void beforeEach() {

		this.validator = new ImageAvailable();
	}

	@Test
	@DisplayName("Test validate existing image url, expected true")
	void givenExistingImageUrl_expectTrue() throws IOException {

		boolean result = validator.validate("https://davidrojo.eu/images/tfm/1.jpg");

		assertThat(result, is(notNullValue()));
		assertThat(result, is(true));
	}

	@Test
	@DisplayName("Test validate not existing image url, expected false")
	void givenNotExistingImageUrl_expectFalse() throws IOException {

		boolean result = validator.validate("https://davidrojo.eu/images/tfm/0.jpg");

		assertThat(result, is(notNullValue()));
		assertThat(result, is(false));
	}

	@Test
	@DisplayName("Test validate malformed url, expected NullPointerException")
	void testMalformedUrl_expectException() {
		assertThrows(IOException.class, () -> validator.validate("abc"));
	}
}
