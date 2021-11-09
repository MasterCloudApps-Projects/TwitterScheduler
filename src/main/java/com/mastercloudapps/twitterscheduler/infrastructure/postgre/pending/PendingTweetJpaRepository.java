package com.mastercloudapps.twitterscheduler.infrastructure.postgre.pending;

import java.time.Instant;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PendingTweetJpaRepository extends JpaRepository<PendingTweetJpaEntity, Long>{

	@Query(value = "SELECT * FROM PENDING_TWEET WHERE PUBLICATION_DATE <= :date ORDER BY PUBLICATION_DATE ASC",
            nativeQuery = true)
	public Collection<PendingTweetJpaEntity> findPendingForPublish(@Param("date") Instant date);
}
