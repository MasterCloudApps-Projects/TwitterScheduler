package com.mastercloudapps.twitterscheduler.application.service;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mastercloudapps.twitterscheduler.application.model.operation.FindOneTweetOperation;
import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;
import com.mastercloudapps.twitterscheduler.domain.tweet.TweetPort;
import com.mastercloudapps.twitterscheduler.mocks.TweetData;

@ExtendWith(MockitoExtension.class)
public class TweetServiceTest {
	
	private TweetService service;
	
	@Mock
	private TweetPort tweetPort;
	
	Tweet tweet;
	
	Tweet anotherTweet;
	
	FindOneTweetOperation findOneOperation;
	
	@BeforeEach
	public void beforeEach() {
		
		this.service = new TweetService(tweetPort);
		
		this.tweet = TweetData.HAPPY_NEW_YEAR.create();
		
		this.anotherTweet = TweetData.MERRY_CHRISTMAS.create();
		
		this.findOneOperation = FindOneTweetOperation.builder()
				.id(tweet.id().id())
				.build();
	}
	
	@Test
	@DisplayName("Test find all tweets with valid request")
	void givenFindAllValidRequest_expectAllTweets() {
		
		List<Tweet> tweets = Stream.of(tweet, anotherTweet)
				.collect(Collectors.toList());
		
		when(tweetPort.findAll()).thenReturn(tweets);
		
		var tweetsResponse = service.findAll().stream().collect(Collectors.toList());
		
		assertThat(tweetsResponse, is(notNullValue()));
		assertThat(tweetsResponse,hasSize(2));
		assertThat(tweetsResponse.get(0), is(notNullValue()));
		assertEquals(tweetsResponse.get(0).id().id(), tweet.id().id());
		assertEquals(tweetsResponse.get(0).message().message(), tweet.message().message());
//		assertEquals(tweetsResponse.get(0).publicationDate().instant(), tweet.publicationDate().instant());
//		assertEquals(tweetsResponse.get(0).createdAt().instant(), tweet.createdAt().instant());
		assertThat(tweetsResponse.get(1), is(notNullValue()));
		assertEquals(tweetsResponse.get(1).id().id(), anotherTweet.id().id());
		assertEquals(tweetsResponse.get(1).message().message(), anotherTweet.message().message());
//		assertEquals(tweetsResponse.get(1).publicationDate().instant(), anotherTweet.publicationDate().instant());
//		assertEquals(tweetsResponse.get(1).createdAt().instant(), anotherTweet.createdAt().instant());
	}
	
	@Test
	@DisplayName("Test find one tweet with existing valid request")
	void givenFindOneExistingValidRequest_expectTweet() {
		
		when(tweetPort.findOne(findOneOperation.getId())).thenReturn(Optional.of(tweet));
		
		var tweetResponse = service.findOne(findOneOperation);
		
		assertThat(tweetResponse, is(notNullValue()));
		assertThat(tweetResponse.isPresent(), is(true));
		assertThat(tweetResponse.get(), instanceOf(Tweet.class));
		assertEquals(tweetResponse.get().id().id(), tweet.id().id());
		assertEquals(tweetResponse.get().message().message(), tweet.message().message());
	}
	
	@Test
	@DisplayName("Test find one tweet with not existing valid request")
	void givenFindOneNotExistingValidRequest_expectEmpty() {
		
		when(tweetPort.findOne(findOneOperation.getId())).thenReturn(Optional.empty());
		
		var tweetResponse = service.findOne(findOneOperation);
		
		assertThat(tweetResponse, is(notNullValue()));
		assertThat(tweetResponse.isPresent(), is(false));
	}
	
	
	
	

}
