package com.mastercloudapps.twitterscheduler.domain.shared;

import java.time.Instant;

import com.mastercloudapps.twitterscheduler.domain.shared.id.DomainObjectId;

public abstract class AggregateRoot <I extends DomainObjectId> extends Entity<I> {

	private static final long serialVersionUID = 4693263567649675537L;

	private NullableInstant updatedAt;

	  protected AggregateRoot(I id) {
	    super(id);
	    updatedAt = new NullableInstant(Instant.now());
	  }

	  public void updated(final NullableInstant updatedAt) {
	    this.updatedAt = updatedAt;
	  }

	  public NullableInstant updatedAt() {
	    return updatedAt;
	  }
}
