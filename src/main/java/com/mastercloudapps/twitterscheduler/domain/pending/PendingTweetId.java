package com.mastercloudapps.twitterscheduler.domain.pending;

import java.util.Objects;

import com.mastercloudapps.twitterscheduler.domain.shared.id.DomainObjectId;

public class PendingTweetId extends DomainObjectId<Long> {

	private static final long serialVersionUID = -1922663636770629060L;
	
	private static final Long DEFAULT_VALUE = Long.MAX_VALUE;

	private PendingTweetId(final Long id) {
		super(id);
	}

	public static PendingTweetId valueOf(final Long id) {
		return new PendingTweetId(id);
	}

	@Override
	public String toString() {
		return "PendingTweetId{" + this.id + "}";
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PendingTweetId that = (PendingTweetId) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public static Long defaultValue() {
		return DEFAULT_VALUE;
	}

}
