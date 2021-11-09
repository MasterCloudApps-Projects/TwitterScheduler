package com.mastercloudapps.twitterscheduler.domain.shared;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public class Url implements ValueObject {

	private static final long serialVersionUID = 5740170789734879291L;

	private final String url;

	private Url(final String url) {
		this.url = requireNonNull(url, "Url cannot be null.");
	}

	public static Url valueOf(final String url) {
		return new Url(url);
	}

	public String url() {
		return url;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Url that = (Url) o;
		return Objects.equals(url, that.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(url);
	}

	@Override
	public String toString() {
		return "Url{url='" + url + '\'' + '}';
	}

}
