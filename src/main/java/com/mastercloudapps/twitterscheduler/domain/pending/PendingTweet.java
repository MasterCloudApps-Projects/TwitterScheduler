package com.mastercloudapps.twitterscheduler.domain.pending;

import static java.util.Objects.requireNonNull;

import java.time.Instant;

import com.mastercloudapps.twitterscheduler.controller.exception.ExpiredPublicationDateException;
import com.mastercloudapps.twitterscheduler.domain.shared.AggregateRoot;
import com.mastercloudapps.twitterscheduler.domain.shared.Message;
import com.mastercloudapps.twitterscheduler.domain.shared.NullableInstant;

public class PendingTweet extends AggregateRoot<PendingTweetId> {

	private static final long serialVersionUID = -9046699696976862412L;

	private Message message;

	private NullableInstant publicationDate;
	
	private NullableInstant createdAt;

	private PendingTweet(final Builder builder) {
		super(builder.pendingTweetId);
		this.message = builder.message;
		this.publicationDate = builder.publicationDate;
		this.createdAt = builder.createdAt;
	}

	public Message message() {

		return message;
	}

	public NullableInstant publicationDate() {

		return publicationDate;
	}
	
	public NullableInstant createdAt() {
		
		return createdAt;
	}

	public static IdStep builder() {

		return new Builder();
	}

	public interface IdStep {

		MessageStep id(final Long pendingTweetId);
	}

	public interface MessageStep {

		PublicationDateStep message(final String message);
	}

	public interface PublicationDateStep {

		CreatedAtStep publicationDate(final Instant instant);
	}
	
	public interface CreatedAtStep {
		
		Build createdAt(final Instant instant);
	}

	public interface Build {

		PendingTweet build();
	}

	public static class Builder implements IdStep, MessageStep, PublicationDateStep,
		CreatedAtStep, Build {

		private PendingTweetId pendingTweetId;

		private Message message;

		private NullableInstant publicationDate;
		
		private NullableInstant createdAt;

		@Override
		public MessageStep id(Long pendingTweetId) {
			this.pendingTweetId = PendingTweetId.valueOf(requireNonNull(pendingTweetId, "Pending Tweet Id cannot be null."));
			return this;
		}

		@Override
		public PublicationDateStep message(String message) {
			this.message = Message.valueOf(requireNonNull(message, "Message cannot be null."));
			return this;
		}

		@Override
		public CreatedAtStep publicationDate(Instant instant) {
			Instant pubDate = requireNonNull(instant, "Publication date cannot be null.");
			NullableInstant niPubDate = new NullableInstant(pubDate);
			this.publicationDate = niPubDate;
			return this;
		}

		@Override
		public Build createdAt(Instant instant) {
			Instant creationDate = requireNonNull(instant, "CreatedAt date cannot be null.");
			NullableInstant niCreatedAt = new NullableInstant(creationDate);
			this.createdAt = niCreatedAt;
			return this;
		}
		
		@Override
		public PendingTweet build() {
			return new PendingTweet(this);
		}
	}
	
}
