package com.mastercloudapps.twitterscheduler.infrastructure.adapter;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.domain.exception.RepositoryException;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweet;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweetPort;
import com.mastercloudapps.twitterscheduler.infrastructure.jpa.pending.PendingTweetJpaEntity;
import com.mastercloudapps.twitterscheduler.infrastructure.jpa.pending.PendingTweetJpaMapper;
import com.mastercloudapps.twitterscheduler.infrastructure.jpa.pending.PendingTweetJpaRepository;

@Component
public class PendingTweetAdapter implements PendingTweetPort {
		
	private static final String DATA_ACCESS_ERROR = "An unexpected error occurred executing a data access operation";

	private PendingTweetJpaRepository pendingTweetJpaRepository;
	
	private PendingTweetJpaMapper mapper;
	
	public PendingTweetAdapter(PendingTweetJpaRepository pendingTweetJpaRepository,
			PendingTweetJpaMapper mapper) {
		
		this.pendingTweetJpaRepository = pendingTweetJpaRepository;
		this.mapper = mapper;
	}
	
	@Override	
	public PendingTweet create(PendingTweet pendingTweet) {
		
		try {
			PendingTweetJpaEntity pendingTweetJpaEntity = mapper.mapCreatePendingTweet(pendingTweet);			
			pendingTweetJpaRepository.save(pendingTweetJpaEntity);
			
			PendingTweetJpaEntity pendingTweetJpaResponse = pendingTweetJpaRepository
					.findById(pendingTweetJpaEntity.getId())
					.orElseThrow();
			
			return mapper.mapEntity(pendingTweetJpaResponse);
			
		} catch (DataAccessException ex) {

			throw new RepositoryException(DATA_ACCESS_ERROR, ex);
		}
	}

	@Override
	public void delete(Long id) {
		
		pendingTweetJpaRepository.deleteById(id);
		
	}

	@Override
	public Collection<PendingTweet> findAll() {
		
		return pendingTweetJpaRepository.findAll().stream()
				.map(entity -> mapper.mapEntity(entity))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<PendingTweet> findOne(Long id) {
		
		final var pendingTweetJpa = pendingTweetJpaRepository.findById(id);

		if (pendingTweetJpa.isPresent()) {
			return Optional.of(mapper.mapEntity(pendingTweetJpa.get()));
		}
		return Optional.empty();
	}

	@Override
	public Collection<PendingTweet> findPendingForPublish(Instant date) {
		
		return pendingTweetJpaRepository.findPendingForPublish(date).stream()
				.map(entity -> mapper.mapEntity(entity))
				.collect(Collectors.toList());
	}

}
