package com.mastercloudapps.twitterscheduler.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.PathVariable;

import com.mastercloudapps.twitterscheduler.controller.dto.tweet.TweetResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "tweets")
public interface TweetApi {
	
	@Operation(
			summary = "Find all tweets available in the account", 
			description = "Find all tweets available in the account", 
			tags = { "tweets" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", 
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = TweetResponse.class)))),
        @ApiResponse(responseCode = "500", description = "internal server error") })
	public Collection<TweetResponse> getTweets();
	
	@Operation(
			summary = "Get the information of a specific tweet and its images", 
			description = "Get the information of a specific tweet and its images", 
			tags = { "tweets" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", 
                content = @Content(schema = @Schema(implementation = TweetResponse.class))),
        @ApiResponse(responseCode = "404", description = "tweet not found") })
	public TweetResponse getTweetById(@PathVariable Long id);

}
