package com.mastercloudapps.twitterscheduler.domain.exception;

import com.mastercloudapps.twitterscheduler.domain.shared.AggregateRoot;
import com.mastercloudapps.twitterscheduler.domain.shared.id.DomainObjectId;

public class RepositoryException extends RuntimeException {

	private static final long serialVersionUID = 4373557876724094773L;

	public RepositoryException(final Exception e) {

		super(e);
	}

	public RepositoryException(final String message) {

		super(message);
	}

	public RepositoryException(final String message, final Throwable cause) {

		super(message, cause);
	}

	public RepositoryException(final String message, final Throwable cause, final AggregateRoot<?> aggregateRoot) {

		super(message + " for the aggregateRoot: [ " + aggregateRoot.getClass().getSimpleName() + " ] with id: [ " + aggregateRoot.id() + " ]",
				cause);
	}

	public RepositoryException(final String message, final Throwable cause, final DomainObjectId id) {

		super(message + " for the Id: [ " + id.getClass().getSimpleName() + " ] with values: [ " + id.id() + " ]",
				cause);
	}
}
