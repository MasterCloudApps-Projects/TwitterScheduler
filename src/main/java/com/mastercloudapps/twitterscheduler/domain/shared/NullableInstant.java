package com.mastercloudapps.twitterscheduler.domain.shared;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.Optional;

public class NullableInstant implements ValueObject {

	private static final Instant MIN = LocalDateTime.of(2000, 1, 1, 0, 0).toInstant(ZoneOffset.UTC);

	private static final Instant MAX = LocalDateTime.of(3000, 1, 1, 0, 0).toInstant(ZoneOffset.UTC);
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
		    .withZone( ZoneId.of("UTC"));

	private static final long serialVersionUID = -9112461170257956003L;

	private final Instant instant;

	public NullableInstant(final Instant instant) {

		this.instant = instant;
	}

	public static NullableInstant nullInstant() {

		return new NullableInstant(null);
	}

	public static NullableInstant fromISO8601(final String isoDate) {

		Instant instant = LocalDateTime.parse(isoDate).toInstant(ZoneOffset.UTC);
		return NullableInstant.fromEpochMilli(instant.toEpochMilli());
	}

	public static NullableInstant fromUtcISO8601(final String isoDate) {

		Instant instant = OffsetDateTime.parse(isoDate).toInstant();
		return NullableInstant.fromEpochMilli(instant.toEpochMilli());
	}

	public static NullableInstant maxInstant() {

		return new NullableInstant(MAX);
	}

	public static NullableInstant minInstant() {

		return new NullableInstant(MIN);
	}

	public static NullableInstant now() {

		return new NullableInstant(Instant.now());
	}

	public static NullableInstant fromEpochMilli(Long milli) {

		return Optional.ofNullable(milli).map(Instant::ofEpochMilli).map(NullableInstant::new).orElse(NullableInstant.nullInstant());
	}

	public long toEpochMilli() {

		return Optional.ofNullable(instant).map(Instant::toEpochMilli).orElse(0L);
	}

	public boolean isNull() {

		return Optional.ofNullable(instant).isEmpty();
	}

	public boolean nonNull() {

		return Optional.ofNullable(instant).isPresent();
	}

	public Instant instant() {

		return instant;
	}
	
	public String getFormatted() {
		
		return formatter.format(instant); 
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		NullableInstant instant1 = (NullableInstant) o;
		return Objects.equals(instant, instant1.instant);
	}

	@Override
	public int hashCode() {
		return Objects.hash(instant);
	}

}
