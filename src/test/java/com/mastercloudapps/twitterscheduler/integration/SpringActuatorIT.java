package com.mastercloudapps.twitterscheduler.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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
@DisplayName("SpringActuator REST tests - RESTAssured")
public class SpringActuatorIT {

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
	@DisplayName("Check that health endpoint is available")
	public void getActuatorHealth() throws Exception {

    	given()
    		.auth()
    		.basic(USER, PASSWORD)
    	.when()
            .get("/actuator/health")
        .then()
            .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("status", equalTo("UP"));
    }
    
    @Test
	@DisplayName("Check that info endpoint is available")
	public void getActuatorInfo() throws Exception {

    	given()
    		.auth()
    		.basic(USER, PASSWORD)
    	.when()
            .get("/actuator/info")
        .then()
            .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(200);
    }
    
    @Test
	@DisplayName("Check that branch main is the used branch")
	public void checkMainBranchUsed() throws Exception {

    	given()
    		.auth()
    		.basic(USER, PASSWORD)
    	.when()
            .get("/actuator/info")
        .then()
            .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("git.branch", equalTo("main"));
    }
    
    @Test
	@DisplayName("Check that togglz endpoint is available")
	public void getActuatorTogglz() throws Exception {

    	given()
    		.auth()
    		.basic(USER, PASSWORD)
    	.when()
            .get("/actuator/togglz")
        .then()
            .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(200);
    }
}
