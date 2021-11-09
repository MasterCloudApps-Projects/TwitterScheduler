package com.mastercloudapps.twitterscheduler.domain.shared;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("shared-domain")
class NullableInstantTest {

	@Test
	  @DisplayName("Test creation instant now, expected not null")
	  void testInstantNow() {

	    var notNullInstant = NullableInstant.now();
	    assertThat(notNullInstant, is(notNullValue()));
	    assertThat(notNullInstant.instant(), is(notNullValue()));
	    assertThat(notNullInstant.isNull(), is(false));
	  }

	  @Test
	  @DisplayName("Test creation instant max, expected not null")
	  void testInstantMax() {

	    var maxInstant = NullableInstant.maxInstant();
	    assertThat(maxInstant, is(notNullValue()));
	    assertThat(maxInstant.instant(), is(notNullValue()));
	    assertThat(maxInstant.isNull(), is(false));
	  }

	  @Test
	  @DisplayName("Test creation instant min, expected not null")
	  void testInstantMin() {

	    var minInstant = NullableInstant.minInstant();
	    assertThat(minInstant, is(notNullValue()));
	    assertThat(minInstant.instant(), is(notNullValue()));
	    assertThat(minInstant.isNull(), is(false));
	  }

	  @Test
	  @DisplayName("Test creation instant null, expected null")
	  void testNull() {

	    var nullInstant = NullableInstant.nullInstant();
	    assertThat(nullInstant, is(notNullValue()));
	    assertThat(nullInstant.instant(), is(nullValue()));
	    assertThat(nullInstant.isNull(), is(true));
	  }

	  @Test
	  @DisplayName("Test creation instant not null, expected not null")
	  void testNonNull() {

	    var nowInstant = NullableInstant.now();
	    assertThat(nowInstant, is(notNullValue()));
	    assertThat(nowInstant.instant(), is(notNullValue()));
	    assertThat(nowInstant.nonNull(), is(true));
	  }

	  @Test
	  @DisplayName("Test creation instant from milli, expected not null")
	  void testFromMilli() {

	    var minInstant = NullableInstant.fromEpochMilli(Long.MIN_VALUE);
	    assertThat(minInstant, is(notNullValue()));
	    assertThat(minInstant.instant(), is(notNullValue()));
	    assertThat(minInstant.nonNull(), is(true));
	  }

	  @Test
	  @DisplayName("Test creation instant from iso 8601 string, expected equal GMT time")
	  void testFromISO() {

	    final var instant = NullableInstant.fromISO8601("2019-01-01T00:00");
	    assertThat(instant.toEpochMilli(), is(1546300800000L));
	  }
}
