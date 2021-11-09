package com.mastercloudapps.twitterscheduler.controller;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingTweetRequest;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingTweetResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "pending")
public interface PendingApi {

	@Operation(
			summary = "Find all pending tweets available in app", 
			description = "Find all pending tweets available in app", 
			tags = { "pending" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", 
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = PendingTweetResponse.class)))),
        @ApiResponse(responseCode = "500", description = "internal server error") })
	public Collection<PendingTweetResponse> getPendingTweets();
	
	@Operation(
			summary = "Get the information of a specific pending tweet and its images", 
			description = "Get the information of a specific pending tweet and its images", 
			tags = { "pending" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", 
                content = @Content(schema = @Schema(implementation = PendingTweetResponse.class))),
        @ApiResponse(responseCode = "404", description = "tweet not found") })
	public ResponseEntity<PendingTweetResponse> getPendingTweetById(@PathVariable Long id);
	
	@Operation(
			summary = "Add a new pending tweet", 
			description = "Add a new pending tweet", 
			tags = { "pending" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "pending tweet successfully created",
                content = @Content(schema = @Schema(implementation = PendingTweetResponse.class))),
        @ApiResponse(responseCode = "500", description = "internal server error") })
	public PendingTweetResponse createPendingTweet(@RequestBody PendingTweetRequest request);
	
	@Operation(
			summary = "Deletes a pending tweet and its images", 
			description = "Deletes a pending tweet and its images", 
			tags = { "pending" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "pending tweet successfully deleted"),
        @ApiResponse(responseCode = "404", description = "pending tweet not found") })
	public ResponseEntity<Void> deletePendingTweet(@PathVariable Long id);
}
