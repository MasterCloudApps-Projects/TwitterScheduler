package com.mastercloudapps.twitterscheduler.infrastructure.jpa.pending;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name="PENDING_IMAGE")
public class PendingTweetImageJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition="TEXT")
	private String url;

	@ManyToOne
    @JoinColumn(name="PENDING_TWEET_ID", nullable=false)
    private PendingTweetJpaEntity pendingTweet;
}