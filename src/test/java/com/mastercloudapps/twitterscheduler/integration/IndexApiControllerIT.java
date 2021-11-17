package com.mastercloudapps.twitterscheduler.integration;

import static io.restassured.RestAssured.given;

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
@DisplayName("IndexApiController REST tests - RESTAssured")
public class IndexApiControllerIT {

	@LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.useRelaxedHTTPSValidation();
    }
    
    @Test
	@DisplayName("Check that index page can be retrieved")
	public void getIndexTest() throws Exception {
    	
    	given()
        	.auth()
            .basic("admin", "admin1")
        .when()
            .get("/")
        .then()
            .assertThat()
                .contentType(ContentType.TEXT)
                .statusCode(200);
    }
}
