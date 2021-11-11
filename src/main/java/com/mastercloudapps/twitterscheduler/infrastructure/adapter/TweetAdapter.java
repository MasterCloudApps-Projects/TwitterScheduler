package com.mastercloudapps.twitterscheduler.infrastructure.adapter;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.domain.exception.RepositoryException;
import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;
import com.mastercloudapps.twitterscheduler.domain.tweet.TweetPort;
import com.mastercloudapps.twitterscheduler.infrastructure.jpa.tweet.TweetJpaEntity;
import com.mastercloudapps.twitterscheduler.infrastructure.jpa.tweet.TweetJpaMapper;
import com.mastercloudapps.twitterscheduler.infrastructure.jpa.tweet.TweetJpaRepository;

@Component
public class TweetAdapter implements TweetPort {

	private static final String DATA_ACCESS_ERROR = "An unexpected error occurred executing a data access operation";

	private TweetJpaRepository tweetJpaRepository;
	
	private TweetJpaMapper mapper;
	
	public TweetAdapter(TweetJpaRepository tweetJpaRepository,
			TweetJpaMapper mapper) {
		
		this.tweetJpaRepository = tweetJpaRepository;
		this.mapper = mapper;
	}
	
	@Override
	public Tweet create(Tweet tweet) {

		try {
			TweetJpaEntity tweetJpaEntity = mapper.mapDomainObject(tweet);			
			tweetJpaRepository.save(tweetJpaEntity);
			
			TweetJpaEntity tweetJpaResponse = tweetJpaRepository
					.findById(tweetJpaEntity.getId())
					.orElseThrow();
			
			return mapper.mapEntity(tweetJpaResponse);
			
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

		return tweetJpaRepository.findAll().stream()
				.map(entity -> mapper.mapEntity(entity))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Tweet> findOne(Long id) {
		
		final var tweetJpa = tweetJpaRepository.findById(id);

		if (tweetJpa.isPresent()) {
			return Optional.of(mapper.mapEntity(tweetJpa.get()));
		}
		return Optional.empty();
	}

}
