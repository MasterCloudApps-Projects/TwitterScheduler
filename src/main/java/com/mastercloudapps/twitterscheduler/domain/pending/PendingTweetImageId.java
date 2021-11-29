package com.mastercloudapps.twitterscheduler.domain.pending;

import java.util.Objects;

import com.mastercloudapps.twitterscheduler.domain.shared.id.DomainObjectId;

public class PendingTweetImageId extends DomainObjectId<Long> {

	private static final long serialVersionUID = -3692626899655506192L;
	
	private static final Long DEFAULT_VALUE = Long.MAX_VALUE;
	
	public PendingTweetImageId(Long id) {
		super(id);
	}
	
	public static PendingTweetImageId valueOf(final Long id) {
		return new PendingTweetImageId(id);
	}

	@Override
	public String toString() {
		return "PendingTweetImageId{" + this.id + "}";
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PendingTweetImageId that = (PendingTweetImageId) o;
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
