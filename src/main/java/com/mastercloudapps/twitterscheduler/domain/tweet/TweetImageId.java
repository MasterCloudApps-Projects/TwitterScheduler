package com.mastercloudapps.twitterscheduler.domain.tweet;

import java.util.Objects;

import com.mastercloudapps.twitterscheduler.domain.shared.id.DomainObjectId;

public class TweetImageId extends DomainObjectId<Long> {

	private static final long serialVersionUID = -961510813076429288L;

	public TweetImageId(Long id) {
		super(id);
	}
	
	public static TweetImageId valueOf(final Long id) {
		return new TweetImageId(id);
	}

	@Override
	public String toString() {
		return "TweetImageId{" + this.id + "}";
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TweetImageId that = (TweetImageId) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
