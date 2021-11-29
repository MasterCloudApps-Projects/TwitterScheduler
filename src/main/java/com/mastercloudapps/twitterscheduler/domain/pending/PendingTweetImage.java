package com.mastercloudapps.twitterscheduler.domain.pending;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import com.mastercloudapps.twitterscheduler.domain.shared.Entity;
import com.mastercloudapps.twitterscheduler.domain.shared.Url;

public class PendingTweetImage extends Entity<PendingTweetImageId> {

	private static final long serialVersionUID = 5298131475420038098L;

	private Url url;

	private PendingTweetImage(Builder builder) {
		super(builder.pendingTweetImageId);
		this.url = builder.url;
	}

	public Url url() {	
		return url;
	}

	public static IdStep builder() {
		return new Builder();
	}

	public interface IdStep {

		UrlStep id(Long pendingTweetImageId);
	}

	public interface UrlStep {

		Build url(String url);
	}

	public interface Build {

		PendingTweetImage build();
	}

	public static class Builder implements IdStep, UrlStep, Build {

		private PendingTweetImageId pendingTweetImageId;

		private Url url;

		@Override
		public UrlStep id(Long pendingTweetImageId) {
			this.pendingTweetImageId = PendingTweetImageId.valueOf(requireNonNull(pendingTweetImageId, "Pending Tweet Image Id cannot be null."));
			return this;
		}

		@Override
		public Build url(String url) {
			this.url = Url.valueOf(requireNonNull(url, "Url cannot be null."));
			return this;
		}

		@Override
		public PendingTweetImage build() {
			return new PendingTweetImage(this);
		}

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		PendingTweetImage that = (PendingTweetImage) o;
		return Objects.equals(url, that.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), url);
	}
}
