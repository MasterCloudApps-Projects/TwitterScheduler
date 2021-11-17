package com.mastercloudapps.twitterscheduler.integration;

import static io.restassured.RestAssured.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@Profile("standalone")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("TweetApiController REST tests - RESTAssured")
public class TweetApiControllerIT {

	@LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.useRelaxedHTTPSValidation();
    }
    
    @Test
	@DisplayName("Check that tweets can be retrieved")
	public void getTweets() throws Exception {

        when()
            .get("/api/tweets")
        .then()
            .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(200);
    }
}
