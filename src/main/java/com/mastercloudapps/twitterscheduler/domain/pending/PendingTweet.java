package com.mastercloudapps.twitterscheduler.domain.pending;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mastercloudapps.twitterscheduler.domain.shared.AggregateRoot;
import com.mastercloudapps.twitterscheduler.domain.shared.Message;
import com.mastercloudapps.twitterscheduler.domain.shared.NullableInstant;

public class PendingTweet extends AggregateRoot<PendingTweetId> {

	private static final long serialVersionUID = -9046699696976862412L;

	private Message message;

	private NullableInstant publicationDate;

	private NullableInstant createdAt;

	private final List<PendingTweetImage> images;

	private PendingTweet(final Builder builder) {
		super(builder.pendingTweetId);
		this.message = builder.message;
		this.publicationDate = builder.publicationDate;
		this.createdAt = builder.createdAt;
		this.images = builder.images;
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
		
		Build images(List<PendingTweetImage> images);

		PendingTweet build();
	}

	public void addImages(PendingTweetImage... images) {
		this.images.addAll(Arrays.stream(images).filter(Objects::nonNull).collect(Collectors.toSet()));
	}

	public void addImages(Collection<PendingTweetImage> images) {
		this.images.addAll(images.stream().filter(Objects::nonNull).collect(Collectors.toSet()));
	}

	public void deleteImages(PendingTweetImage... images) {
		this.images.removeAll(Arrays.stream(images).filter(Objects::nonNull).collect(Collectors.toSet()));
	}

	public void deleteImages(Collection<PendingTweetImage> images) {
		this.images.removeAll(images.stream().filter(Objects::nonNull).collect(Collectors.toSet()));
	}

	public void deleteImages() {
		this.images.clear();
	}

	public List<PendingTweetImage> getImages() {
		return Collections.unmodifiableList(images);
	}

	public static class Builder implements IdStep, MessageStep, PublicationDateStep,
		CreatedAtStep, Build {

		private PendingTweetId pendingTweetId;

		private Message message;

		private NullableInstant publicationDate;

		private NullableInstant createdAt;
		
		private List<PendingTweetImage> images = new ArrayList<>();

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

		@Override
		public Build images(List<PendingTweetImage> images) {
			this.images = Objects.requireNonNull(images);
			return this;
		}
	}

}
