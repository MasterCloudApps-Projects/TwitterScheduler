package com.mastercloudapps.twitterscheduler.domain.pending;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

public interface PendingTweetPort {

	public PendingTweet create(PendingTweet pendingTweet);
	
	public void delete(Long id);
	
	public Collection<PendingTweet> findAll();
	
	public Optional<PendingTweet> findOne(Long id);
	
	public Collection<PendingTweet> findPendingForPublish(Instant date);

}
