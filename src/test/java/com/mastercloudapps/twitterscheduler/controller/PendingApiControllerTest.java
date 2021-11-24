package com.mastercloudapps.twitterscheduler.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.togglz.core.manager.FeatureManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercloudapps.twitterscheduler.application.usecase.CreatePendingTweetUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.DeletePendingTweetUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.FindAllPendingTweetUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.FindOnePendingTweetUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.PublishPendingTweetOnDemandUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.PublishPendingTweetsUseCase;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingTweetRequest;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingTweetResponse;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweet;
import com.mastercloudapps.twitterscheduler.domain.tweet.Tweet;
import com.mastercloudapps.twitterscheduler.mocks.PendingTweetData;
import com.mastercloudapps.twitterscheduler.mocks.TweetData;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("PendingApiController Unit tests using mocks")
class PendingApiControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private FeatureManager featureManager;
	
	@MockBean
	private CreatePendingTweetUseCase createTweetUseCase;
	
	@MockBean
	private DeletePendingTweetUseCase deleteUseCase;
	
	@MockBean
	private FindAllPendingTweetUseCase findAllUseCase;
	
	@MockBean
	private FindOnePendingTweetUseCase findOneUseCase;
	
	@MockBean
	private PublishPendingTweetOnDemandUseCase publishPendingTweetOnDemandUseCase;
	
	@MockBean
	private PublishPendingTweetsUseCase publishPendingTweetsUseCase;

	PendingTweetRequest pendingTweetRequest;

	PendingTweetResponse pendingTweetResponse;

	PendingTweet pendingTweet;
	
	PendingTweet anotherPendingTweet;
	
	Tweet onDemandTweet;

	@BeforeEach
	void setup() {

		pendingTweet = PendingTweetData.HAPPY_NEW_YEAR.create();
		
		anotherPendingTweet = PendingTweetData.MERRY_CHRISTMAS.create();
		
		pendingTweetRequest = PendingTweetRequest
				.builder()
				.message(pendingTweet.message().message())
				.publicationDate(pendingTweet.publicationDate().instant().toString())
				.build();
		
		onDemandTweet = TweetData.HAPPY_BIRTHDAY_ON_DEMAND.create(); 
		
	}

	@Test
	@DisplayName("Post pending tweet, expect created")
	void createPendingTweetTest() throws Exception {

		when(createTweetUseCase.create(Mockito.any()))
			.thenReturn(pendingTweet);

		mvc.perform(
				post("/api/pending/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(pendingTweetRequest)))
		.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Delete pending tweet, expect deleted")
	void deletePendingTweetTest() throws Exception {

		doNothing().when(deleteUseCase).delete(Mockito.any());

		mvc.perform(
				delete("/api/pending/" + pendingTweet.id().id())
					.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Find all pending tweets, expect all pending tweets")
	void findAllPendingTweetTest() throws Exception {
	
		List<PendingTweet> pendingTweets = Stream.of(pendingTweet, anotherPendingTweet)
				.collect(Collectors.toList());
		
		when(findAllUseCase.findAll()).thenReturn(pendingTweets);
		
		mvc.perform(
	    		get("/api/pending/")
	    		.contentType(MediaType.APPLICATION_JSON)
	    	)
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$", hasSize(2)))
	    	.andExpect(jsonPath("$[0].id", equalTo(Math.toIntExact(pendingTweet.id().id()))))
	    	.andExpect(jsonPath("$[0].message", equalTo(pendingTweet.message().message())))
	    	.andExpect(jsonPath("$[1].id", equalTo(Math.toIntExact(anotherPendingTweet.id().id()))))
	    	.andExpect(jsonPath("$[1].message", equalTo(anotherPendingTweet.message().message())));	    	
	}
	
	@Test
	@DisplayName("Find one pending tweet by id with existing, expect pending tweet")
	void findOnePendingTweetTest() throws Exception {
		
		when(findOneUseCase.findOne(any())).thenReturn(Optional.of(pendingTweet));
		
		mvc.perform(
	    		get("/api/pending/" + pendingTweet.id().id())
	    		.contentType(MediaType.APPLICATION_JSON)
	    	)
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.id", equalTo(Math.toIntExact(pendingTweet.id().id()))))
	    	.andExpect(jsonPath("$.message", equalTo(pendingTweet.message().message())));
	}
	
	@Test
	@DisplayName("Find one pending tweet by id with not existing, expect not found")
	void findOneTweetTestWithNotExisting() throws Exception {
		
		when(findOneUseCase.findOne(any())).thenReturn(Optional.empty());
		
		mvc.perform(
	    		get("/api/pending/" + new Random().nextLong())
	    		.contentType(MediaType.APPLICATION_JSON)
	    	)
	    	.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Publish on demand pending tweet when feature not active, expect not allowed")
	void publishOnDemandPendingTweetWhenFeatureNotActiveTest() throws Exception {

		when(featureManager.isActive(Mockito.any()))
			.thenReturn(false);

		mvc.perform(
				post("/api/pending/" + pendingTweet.id().id() + "/publish")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	@DisplayName("Publish on demand pending tweet when feature active and pending doesn't exist, expect not found")
	void publishOnDemandNotExistingPendingTweetWhenFeatureActiveTest() throws Exception {

		when(featureManager.isActive(Mockito.any()))
			.thenReturn(true);
		
		when(publishPendingTweetOnDemandUseCase.publishImmediatly(any()))
			.thenReturn(Optional.empty());

		mvc.perform(
				post("/api/pending/" + pendingTweet.id().id() + "/publish")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Publish on demand pending tweet when feature active and pending exist, expect OK")
	void publishOnDemandExistingPendingTweetWhenFeatureActiveTest() throws Exception {

		when(featureManager.isActive(Mockito.any()))
			.thenReturn(true);
		
		when(publishPendingTweetOnDemandUseCase.publishImmediatly(any()))
			.thenReturn(Optional.of(onDemandTweet));

		mvc.perform(
				post("/api/pending/" + pendingTweet.id().id() + "/publish")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.tweetId", equalTo(Math.toIntExact(onDemandTweet.id().id()))));
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}