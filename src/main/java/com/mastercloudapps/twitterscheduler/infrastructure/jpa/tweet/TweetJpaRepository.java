package com.mastercloudapps.twitterscheduler.infrastructure.jpa.tweet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetJpaRepository extends JpaRepository<TweetJpaEntity, Long>{

}
