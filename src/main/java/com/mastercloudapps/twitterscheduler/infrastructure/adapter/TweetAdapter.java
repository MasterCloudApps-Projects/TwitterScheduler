package com.mastercloudapps.twitterscheduler.infrastructure.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.domain.exception.RepositoryException;
import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;
import com.mastercloudapps.twitterscheduler.domain.tweet.TweetPort;
import com.mastercloudapps.twitterscheduler.infrastructure.jpa.tweet.TweetImageJpaMapper;
import com.mastercloudapps.twitterscheduler.infrastructure.jpa.tweet.TweetJpaEntity;
import com.mastercloudapps.twitterscheduler.infrastructure.jpa.tweet.TweetJpaMapper;
import com.mastercloudapps.twitterscheduler.infrastructure.jpa.tweet.TweetJpaRepository;

@Component
public class TweetAdapter implements TweetPort {

	private static final String DATA_ACCESS_ERROR = "An unexpected error occurred executing a data access operation";
	
	private TweetJpaRepository tweetJpaRepository;
	
	private TweetJpaMapper tweetMapper;
	
	private TweetImageJpaMapper tweetImageMapper;
	
	public TweetAdapter(final TweetJpaRepository tweetJpaRepository,
			final TweetJpaMapper tweetMapper,
			final TweetImageJpaMapper tweetImageMapper) {
		
		this.tweetJpaRepository = tweetJpaRepository;
		this.tweetMapper = tweetMapper;
		this.tweetImageMapper = tweetImageMapper;
	}
	
	@Override
	public Tweet create(Tweet tweet) {

		try {
			TweetJpaEntity tweetJpaEntity = tweetMapper.mapDomainObject(tweet);			
			
			final var dbImages = tweet.getImages().stream()
					.map(image -> tweetImageMapper.mapDomainObject(image, tweetJpaEntity))
					.collect(Collectors.toList());
			tweetJpaEntity.setImages(dbImages);
			
			tweetJpaRepository.save(tweetJpaEntity);
			
			TweetJpaEntity tweetJpaResponse = tweetJpaRepository
					.findById(tweetJpaEntity.getId())
					.orElseThrow();
			
			final var createdTweet = tweetMapper.mapEntity(tweetJpaResponse);
			final var domainImages = tweetJpaResponse.getImages().stream()
					.map(image -> tweetImageMapper.mapEntity(image))
					.collect(Collectors.toList());
			createdTweet.addImages(domainImages);
			
			return tweetMapper.mapEntity(tweetJpaResponse);
			
		} catch (DataAccessException ex) {

			throw new RepositoryException(DATA_ACCESS_ERROR, ex);
		}
	}

	@Override
	public void delete(Long id) {
		
		tweetJpaRepository.deleteById(id);
		
	}

	@Override
	public Collection<Tweet> findAll() {

		final var tweetsDb = tweetJpaRepository.findAll();
		
		List<Tweet> tweets = new ArrayList<>();
		
		for (TweetJpaEntity tweetDb : tweetsDb) {
			final var tweet = tweetMapper.mapEntity(tweetDb);
			final var domainImages = tweetDb.getImages().stream()
					.map(image -> tweetImageMapper.mapEntity(image))
					.collect(Collectors.toList());
			tweet.addImages(domainImages);
			tweets.add(tweet);
		}

		return tweets;
	}

	@Override
	public Optional<Tweet> findOne(Long id) {
		
		final var tweetJpa = tweetJpaRepository.findById(id);

		if (tweetJpa.isPresent()) {
			final var tweet = tweetMapper.mapEntity(tweetJpa.get());
			final var domainImages = tweetJpa.get().getImages().stream()
					.map(image -> tweetImageMapper.mapEntity(image))
					.collect(Collectors.toList());
			tweet.addImages(domainImages);
			
			return Optional.of(tweet);
		}
		return Optional.empty();
	}

}