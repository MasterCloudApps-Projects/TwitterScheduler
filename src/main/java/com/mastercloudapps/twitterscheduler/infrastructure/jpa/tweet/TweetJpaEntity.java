package com.mastercloudapps.twitterscheduler.infrastructure.jpa.tweet;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mastercloudapps.twitterscheduler.domain.tweet.PublicationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name="TWEET")
public class TweetJpaEntity {

	@Id
	private Long id;
	
	@Column(columnDefinition="TEXT")
	private String message;
	
	@Column(columnDefinition="TEXT")
	private String url;
	
	private Instant requestedPublicationDate;
	
	private Instant publishedAt;
	
	private Instant createdAt;
	
	@Enumerated(EnumType.STRING)
	private PublicationType publicationType;
	
}
