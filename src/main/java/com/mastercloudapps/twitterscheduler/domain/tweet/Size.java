package com.mastercloudapps.twitterscheduler.domain.tweet;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import com.mastercloudapps.twitterscheduler.domain.shared.ValueObject;

public class Size implements ValueObject {

	private static final long serialVersionUID = -6422141827884275696L;
	
	private final Long size;

	private Size(final Long size) {
		this.size = requireNonNull(size, "Size cannot be null.");
	}

	public static Size valueOf(final Long size) {
		return new Size(size);
	}

	public Long size() {
		return size;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Size that = (Size) o;
		return Objects.equals(size, that.size);
	}

	@Override
	public int hashCode() {
		return Objects.hash(size);
	}

	@Override
	public String toString() {
		return "Size{size='" + size + '\'' + '}';
	}

}
