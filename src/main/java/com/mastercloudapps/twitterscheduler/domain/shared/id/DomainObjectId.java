package com.mastercloudapps.twitterscheduler.domain.shared.id;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Objects;

import com.mastercloudapps.twitterscheduler.domain.shared.Identifiable;

public class DomainObjectId<I extends Serializable> implements Identifiable<I> {

	private static final long serialVersionUID = 2970754499849435562L;

	protected final I id;

	public DomainObjectId(I id) {
		this.id = requireNonNull(id, "Id cannot be null.");
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
		DomainObjectId<?> that = (DomainObjectId<?>) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "DomainObjectId{id=" + id + '}';
	}



}
