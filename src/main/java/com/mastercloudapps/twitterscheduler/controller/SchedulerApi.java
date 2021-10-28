package com.mastercloudapps.twitterscheduler.controller;

import org.springframework.http.ResponseEntity;

import com.mastercloudapps.twitterscheduler.controller.dto.scheduler.SchedulerResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "scheduler")
public interface SchedulerApi {

	@Operation(
			summary = "Get scheduler status", 
			description = "Get scheduler status", 
			tags = { "scheduler" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", 
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = SchedulerResponse.class)))),
        @ApiResponse(responseCode = "500", description = "internal server error") })
	public ResponseEntity<SchedulerResponse> getStatus();	
	
}
