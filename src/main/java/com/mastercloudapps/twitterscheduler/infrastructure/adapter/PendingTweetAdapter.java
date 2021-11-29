package com.mastercloudapps.twitterscheduler.infrastructure.adapter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.mastercloudapps.twitterscheduler.domain.exception.RepositoryException;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweet;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweetPort;
import com.mastercloudapps.twitterscheduler.infrastructure.jpa.pending.PendingTweetImageJpaMapper;
import com.mastercloudapps.twitterscheduler.infrastructure.jpa.pending.PendingTweetJpaEntity;
import com.mastercloudapps.twitterscheduler.infrastructure.jpa.pending.PendingTweetJpaMapper;
import com.mastercloudapps.twitterscheduler.infrastructure.jpa.pending.PendingTweetJpaRepository;

@Component
public class PendingTweetAdapter implements PendingTweetPort {
		
	private static final String DATA_ACCESS_ERROR = "An unexpected error occurred executing a data access operation";

	private PendingTweetJpaRepository pendingTweetJpaRepository;

	private PendingTweetJpaMapper pendingTweetMapper;
		
	private PendingTweetImageJpaMapper pendingTweetImageMapper;
	
	public PendingTweetAdapter(final PendingTweetJpaRepository pendingTweetJpaRepository,
			final PendingTweetJpaMapper pendingTweetMapper,
			final PendingTweetImageJpaMapper pendingTweetImageMapper) {
		
		this.pendingTweetJpaRepository = pendingTweetJpaRepository;
		this.pendingTweetMapper = pendingTweetMapper;
		this.pendingTweetImageMapper = pendingTweetImageMapper;
	}
	
	@Override	
	public PendingTweet create(PendingTweet pendingTweet) {
		
		try {
			PendingTweetJpaEntity pendingTweetJpaEntity = pendingTweetMapper.mapCreatePendingTweet(pendingTweet);
			
			final var dbImages = pendingTweet.getImages().stream()
					.map(image -> pendingTweetImageMapper.mapCreatePendingTweetImage(image, pendingTweetJpaEntity))
					.collect(Collectors.toList());
			
			pendingTweetJpaEntity.setImages(dbImages);
			pendingTweetJpaRepository.save(pendingTweetJpaEntity);
			
			PendingTweetJpaEntity pendingTweetJpaResponse = pendingTweetJpaRepository
					.findById(pendingTweetJpaEntity.getId())
					.orElseThrow();
			
			final var createdPendingTweet = pendingTweetMapper.mapEntity(pendingTweetJpaResponse);
			
			final var domainImages = pendingTweetJpaResponse.getImages().stream()
					.map(image -> pendingTweetImageMapper.mapEntity(image))
					.collect(Collectors.toList());
			createdPendingTweet.addImages(domainImages);

			return createdPendingTweet;
			
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
		
		final var pendingTweetsDb = pendingTweetJpaRepository.findAll();
		
		List<PendingTweet> pendingTweets = new ArrayList<>();
		
		for (PendingTweetJpaEntity pendingTweetDb : pendingTweetsDb) {
			final var pendingTweet = pendingTweetMapper.mapEntity(pendingTweetDb);
			final var domainImages = pendingTweetDb.getImages().stream()
					.map(image -> pendingTweetImageMapper.mapEntity(image))
					.collect(Collectors.toList());
			pendingTweet.addImages(domainImages);
			pendingTweets.add(pendingTweet);
		}
		
		return pendingTweets;
	}

	@Override
	public Optional<PendingTweet> findOne(Long id) {
		
		final var pendingTweetJpa = pendingTweetJpaRepository.findById(id);

		if (pendingTweetJpa.isPresent()) {			
			final var pendingTweet = pendingTweetMapper.mapEntity(pendingTweetJpa.get());
			final var domainImages = pendingTweetJpa.get().getImages().stream()
					.map(image -> pendingTweetImageMapper.mapEntity(image))
					.collect(Collectors.toList());
			pendingTweet.addImages(domainImages);
			
			return Optional.of(pendingTweet);
		}
		return Optional.empty();
	}

	@Override
	public Collection<PendingTweet> findPendingForPublish(Instant date) {
		
		final var pendingTweetsDb = pendingTweetJpaRepository.findPendingForPublish(date);
		
		List<PendingTweet> pendingTweets = new ArrayList<>();
		
		for (PendingTweetJpaEntity pendingTweetDb : pendingTweetsDb) {
			final var pendingTweet = pendingTweetMapper.mapEntity(pendingTweetDb);
			final var domainImages = pendingTweetDb.getImages().stream()
					.map(image -> pendingTweetImageMapper.mapEntity(image))
					.collect(Collectors.toList());
			pendingTweet.addImages(domainImages);
			pendingTweets.add(pendingTweet);
		}
		
		return pendingTweets;
	}

}