package com.mastercloudapps.twitterscheduler.domain.tweet;

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
import com.mastercloudapps.twitterscheduler.domain.shared.Url;

public class Tweet extends AggregateRoot<TweetId> {
	
	private static final long serialVersionUID = 2394451305012148462L;

	private Message message;
	
	private Url url;

	private NullableInstant requestedPublicationDate;
	
	private NullableInstant publishedAt;
	
	private NullableInstant createdAt;
	
	private PublicationType publicationType;
	
	private final List<TweetImage> images;

	private Tweet(final Builder builder) {
		super(builder.tweetId);
		this.message = builder.message;
		this.url = builder.url;
		this.requestedPublicationDate = builder.requestedPublicationDate;
		this.publishedAt = builder.publishedAt;
		this.createdAt = builder.createdAt;
		this.publicationType = builder.publicationType;
		this.images = builder.images;
	}

	public Message message() {

		return message;
	}
	
	public Url url() {

		return url;
	}

	public NullableInstant requestedPublicationDate() {

		return requestedPublicationDate;
	}
	
	public NullableInstant publishedAt() {
		
		return publishedAt;
	}

	public NullableInstant createdAt() {
		
		return createdAt;
	}
	
	public PublicationType publicationType() {
		
		return publicationType;
	}

	public static IdStep builder() {

		return new Builder();
	}

	public interface IdStep {

		MessageStep id(final Long pendingTweetId);
	}

	public interface MessageStep {

		UrlStep message(final String message);
	}

	public interface UrlStep {

		RequestedPublicationDateStep url(final String url);
	}
	
	public interface RequestedPublicationDateStep {

		PublishedAtStep requestedPublicationDate(final Instant instant);
	}
	
	public interface PublishedAtStep {

		CreatedAtStep publishedAt(final Instant instant);
	}
	
	public interface CreatedAtStep {
		
		PublicationTypeStep createdAt(final Instant instant);
	}
	
	public interface PublicationTypeStep {
		
		Build publicationType(PublicationType publicationType);
	}

	public interface Build {

		Build images(List<TweetImage> images);
		
		Tweet build();
	}

	public void addImages(TweetImage... images) {
		this.images.addAll(Arrays.stream(images).filter(Objects::nonNull).collect(Collectors.toSet()));
	}

	public void addImages(Collection<TweetImage> images) {
		this.images.addAll(images.stream().filter(Objects::nonNull).collect(Collectors.toSet()));
	}

	public void deleteImages(TweetImage... images) {
		this.images.removeAll(Arrays.stream(images).filter(Objects::nonNull).collect(Collectors.toSet()));
	}

	public void deleteImages(Collection<TweetImage> images) {
		this.images.removeAll(images.stream().filter(Objects::nonNull).collect(Collectors.toSet()));
	}

	public void deleteImages() {
		this.images.clear();
	}

	public List<TweetImage> getImages() {
		return Collections.unmodifiableList(images);
	}
	
	public static class Builder implements IdStep, MessageStep, UrlStep, 
		RequestedPublicationDateStep, PublishedAtStep, CreatedAtStep, PublicationTypeStep, Build {

		private TweetId tweetId;

		private Message message;
		
		private Url url;

		private NullableInstant requestedPublicationDate;
		
		private NullableInstant publishedAt;
		
		private NullableInstant createdAt;
		
		private PublicationType publicationType;
		
		private List<TweetImage> images = new ArrayList<>();

		@Override
		public MessageStep id(Long tweetId) {
			this.tweetId = TweetId.valueOf(requireNonNull(tweetId, "Tweet Id cannot be null."));
			return this;
		}

		@Override
		public UrlStep message(String message) {
			this.message = Message.valueOf(requireNonNull(message, "Message cannot be null."));
			return this;
		}
		
		@Override
		public RequestedPublicationDateStep url(String url) {
			this.url = Url.valueOf(requireNonNull(url, "Url cannot be null."));
			return this;
		}

		@Override
		public PublishedAtStep requestedPublicationDate(Instant instant) {
			Instant reqPubDate = requireNonNull(instant, "Requested publication date cannot be null.");
			this.requestedPublicationDate = new NullableInstant(reqPubDate);
			return this;
		}

		@Override
		public CreatedAtStep publishedAt(Instant instant) {
			Instant pubDate = requireNonNull(instant, "published at date cannot be null.");
			this.publishedAt = new NullableInstant(pubDate);
			return this;
		}
		
		@Override
		public PublicationTypeStep createdAt(Instant instant) {
			Instant creationDate = requireNonNull(instant, "CreatedAt date cannot be null.");
			NullableInstant niCreatedAt = new NullableInstant(creationDate);
			this.createdAt = niCreatedAt;
			return this;
		}
		
		@Override
		public Build publicationType(PublicationType publicationType) {
			this.publicationType = Objects.requireNonNull(publicationType);
		      return this;
		}
		
		@Override
		public Tweet build() {
			return new Tweet(this);
		}

		@Override
		public Build images(List<TweetImage> images) {
			this.images = Objects.requireNonNull(images);
			return this;
		}
	}
	
}
