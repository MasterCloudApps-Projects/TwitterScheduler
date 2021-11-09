package com.mastercloudapps.twitterscheduler.domain.shared;

import java.util.Objects;

import com.mastercloudapps.twitterscheduler.domain.shared.id.DomainObjectId;

public abstract class Entity<I extends DomainObjectId> implements Identifiable<I> {

	private static final long serialVersionUID = 3975656712044986935L;

	private final I id;

	protected Entity(I id) {
		this.id = Objects.requireNonNull(id, "id must not be null");
	}

	@Override
	public I id() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Entity<?> entity = (Entity<?>) o;
		return Objects.equals(id, entity.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return String.format("%s[%s]", getClass().getSimpleName(), id);
	}
}
