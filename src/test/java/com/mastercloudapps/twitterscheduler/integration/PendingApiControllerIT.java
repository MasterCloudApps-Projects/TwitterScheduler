package com.mastercloudapps.twitterscheduler.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingTweetRequest;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingTweetResponse;
import com.mastercloudapps.twitterscheduler.domain.pending.PendingTweet;
import com.mastercloudapps.twitterscheduler.mocks.PendingTweetData;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@Profile("standalone")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("PendingApiController REST tests - RESTAssured")
public class PendingApiControllerIT {

	@LocalServerPort
    int port;
	
	private final String USER = "admin";
	
	private final String PASSWORD = "admin1";

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.useRelaxedHTTPSValidation();
    }
    
    @Test
	@DisplayName("Check that pending tweets can be retrieved")
	public void getPendingTweetsTest() throws Exception {
    	
        given()
        	.auth()
            	.basic(USER, PASSWORD)
        .when()
            .get("/api/pending")
        .then()
            .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(200);
    }
    
    
    @Test
    @DisplayName("Add new pending tweet and check that it has been created")
	public void createPendingTweetTest() throws Exception {

    	// create a pending tweet
    	PendingTweet pendingTweet = PendingTweetData.HAPPY_NEW_YEAR.create();
		PendingTweetRequest request = PendingTweetRequest
				.builder()
				.message(pendingTweet.message().message())
				.publicationDate(pendingTweet.publicationDate().instant().toString())
				.build();
    	
        PendingTweetResponse createdPendingTweet = 
            given()
                .auth()
                    .basic(USER, PASSWORD)
                .request()
                    .body(this.getPendingTweetRequestAsJsonString(request))
                    .contentType(ContentType.JSON)
            .when()
                .post("api/pending")
            .then()
                .assertThat()
                .statusCode(201)
                .body("message", equalTo(request.getMessage()))
                .extract().as(PendingTweetResponse.class);

        // check that the pending tweet has been created
        given()
        	.auth()
            	.basic(USER, PASSWORD)
        .when()
            .get("/api/pending/{id}", createdPendingTweet.getId())
        .then()
             .assertThat()
             .statusCode(200)
             .body("message", equalTo(request.getMessage()));
    }
    
    @Test
	@DisplayName("Delete a pending tweet and check that it has been deleted")
	public void deletePendingTweetTest() throws Exception {

        // create a pending tweet
    	PendingTweet pendingTweet = PendingTweetData.HAPPY_NEW_YEAR.create();
		PendingTweetRequest request = PendingTweetRequest
				.builder()
				.message(pendingTweet.message().message())
				.publicationDate(pendingTweet.publicationDate().instant().toString())
				.build();
    	
        PendingTweetResponse createdPendingTweet = 
            given()
                .auth()
                    .basic(USER, PASSWORD)
                .request()
                    .body(this.getPendingTweetRequestAsJsonString(request))
                    .contentType(ContentType.JSON).
            when()
                .post("api/pending").
            then()
                .assertThat()
                .statusCode(201)
                .body("message", equalTo(request.getMessage()))
                .extract().as(PendingTweetResponse.class);
        
        // delete created pending tweet
        given()
            .auth()
                .basic(USER, PASSWORD)
        .when()
             .delete("/api/pending/{id}", createdPendingTweet.getId())
        .then()
             .assertThat()
                .statusCode(200);

        // check that pending tweet has been deleted
        given()
    		.auth()
        		.basic(USER, PASSWORD)
        .when()
             .get("/api/pending/{id}", createdPendingTweet.getId())
        .then()
             .assertThat()
                .statusCode(404);    
    }

    private String getPendingTweetRequestAsJsonString(PendingTweetRequest request) 
    		throws JsonProcessingException {
    	
    	String json = new ObjectMapper().writeValueAsString(ImmutableMap.builder()
    			.put("message", request.getMessage())
    			.put("publicationDate", request.getPublicationDate())
    			.build()); 
    			
    	return json;
    }
   
}
