package com.mastercloudapps.twitterscheduler.domain.tweet;

import java.util.Objects;

import com.mastercloudapps.twitterscheduler.domain.shared.id.DomainObjectId;

public class TweetId extends DomainObjectId<Long> {

	private static final long serialVersionUID = 4154971056356887945L;

	private TweetId(final Long id) {
		super(id);
	}

	public static TweetId valueOf(final Long id) {
		return new TweetId(id);
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
		TweetId that = (TweetId) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
