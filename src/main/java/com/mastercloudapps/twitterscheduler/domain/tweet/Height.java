package com.mastercloudapps.twitterscheduler.domain.tweet;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import com.mastercloudapps.twitterscheduler.domain.shared.ValueObject;

public class Height implements ValueObject {

	private static final long serialVersionUID = -6422141827884275696L;
	
	private final Integer height;

	private Height(final Integer height) {
		this.height = requireNonNull(height, "Height cannot be null.");
	}

	public static Height valueOf(final Integer height) {
		return new Height(height);
	}

	public Integer height() {
		return height;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Height that = (Height) o;
		return Objects.equals(height, that.height);
	}

	@Override
	public int hashCode() {
		return Objects.hash(height);
	}

	@Override
	public String toString() {
		return "Height{height='" + height + '\'' + '}';
	}

}
