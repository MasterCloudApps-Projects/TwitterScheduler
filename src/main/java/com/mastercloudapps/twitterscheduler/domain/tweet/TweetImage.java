package com.mastercloudapps.twitterscheduler.domain.tweet;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import com.mastercloudapps.twitterscheduler.domain.shared.Entity;

public class TweetImage extends Entity<TweetImageId> {

	private static final long serialVersionUID = -6578177746889786426L;
	
	private Size size;
	
	private Type type;
	
	private Width width;
	
	private Height height;

	private TweetImage(Builder builder) {
		super(builder.tweetImageId);
		this.size = builder.size;
		this.type = builder.type;
		this.width = builder.width;
		this.height = builder.height;
	}
	
	public Size size() {	
		return size;
	}
	
	public Type type() {	
		return type;
	}
	
	public Width width() {	
		return width;
	}
	
	public Height height() {	
		return height;
	}
	
	public static IdStep builder() {
		return new Builder();
	}
	
	public interface IdStep {

		SizeStep id(Long tweetImageId);
	}
	
	public interface SizeStep {

		TypeStep size(Long size);
	}
	
	public interface TypeStep {

		WidthStep type(String type);
	}
	
	public interface WidthStep {

		HeightStep width(Integer width);
	}
	
	public interface HeightStep {

		Build height(Integer height);
	}
	
	public interface Build {

		TweetImage build();
	}
	
	public static class Builder implements IdStep, SizeStep, TypeStep, WidthStep, HeightStep, Build {

		private TweetImageId tweetImageId;
		
		private Size size;
		
		private Type type;
		
		private Width width;
		
		private Height height;

		@Override
		public SizeStep id(Long tweetImageId) {
			this.tweetImageId = TweetImageId.valueOf(requireNonNull(tweetImageId, "Tweet Image Id cannot be null."));
			return this;
		}
		
		@Override
		public TypeStep size(Long size) {
			this.size = Size.valueOf(requireNonNull(size, "Size cannot be null."));
			return this;
		}
		
		@Override
		public WidthStep type(String type) {
			this.type = Type.valueOf(requireNonNull(type, "Type cannot be null."));
			return this;
		}
		
		@Override
		public HeightStep width(Integer width) {
			this.width = Width.valueOf(requireNonNull(width, "Width cannot be null."));
			return this;
		}
		
		@Override
		public Build height(Integer height) {
			this.height = Height.valueOf(requireNonNull(height, "Height cannot be null."));
			return this;
		}
		
		@Override
		public TweetImage build() {
			return new TweetImage(this);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		TweetImage that = (TweetImage) o;
		return Objects.equals(size, that.size)
				&& Objects.equals(type, that.type)
				&& Objects.equals(width, that.width)
				&& Objects.equals(height, that.height);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), size, type, width, height);
	}
}
