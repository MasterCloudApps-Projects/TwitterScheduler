package com.mastercloudapps.twitterscheduler.domain.tweet;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import com.mastercloudapps.twitterscheduler.domain.shared.ValueObject;

public class Width implements ValueObject {

	private static final long serialVersionUID = -6422141827884275696L;
	
	private final Integer width;

	private Width(final Integer height) {
		this.width = requireNonNull(height, "Width cannot be null.");
	}

	public static Width valueOf(final Integer width) {
		return new Width(width);
	}

	public Integer width() {
		return width;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Width that = (Width) o;
		return Objects.equals(width, that.width);
	}

	@Override
	public int hashCode() {
		return Objects.hash(width);
	}

	@Override
	public String toString() {
		return "Width{width='" + width + '\'' + '}';
	}

}
