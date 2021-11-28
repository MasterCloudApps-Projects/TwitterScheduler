package com.mastercloudapps.twitterscheduler.infrastructure.jpa.tweet;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="TWEET_IMAGE")
public class TweetImageJpaEntity {

	@Id
	private Long id;

	private Long size;

	@Column(columnDefinition="TEXT")
	private String type;

	private Integer width;

	private Integer height;

	@ManyToOne
	@JoinColumn(name="TWEET_ID", nullable=false)
	private TweetJpaEntity tweet;

}

