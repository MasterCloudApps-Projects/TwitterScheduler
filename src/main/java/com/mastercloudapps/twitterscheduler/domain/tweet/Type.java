package com.mastercloudapps.twitterscheduler.domain.tweet;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import com.mastercloudapps.twitterscheduler.domain.shared.ValueObject;

public class Type implements ValueObject {

	private static final long serialVersionUID = 2492495822687908721L;

	private final String type;

	private Type(final String type) {
		this.type = requireNonNull(type, "Type cannot be null.");
	}

	public static Type valueOf(final String type) {
		return new Type(type);
	}

	public String type() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Type that = (Type) o;
		return Objects.equals(type, that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

	@Override
	public String toString() {
		return "Type{type='" + type + '\'' + '}';
	}

}
