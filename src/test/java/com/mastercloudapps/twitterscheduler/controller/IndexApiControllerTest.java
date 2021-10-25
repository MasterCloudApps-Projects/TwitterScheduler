package com.mastercloudapps.twitterscheduler.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@DisplayName("IndexApiController Unit tests")
class IndexApiControllerTest {

	@Autowired
	private IndexApiController indexApiController;
	
	@Test
	@DisplayName("Get index HTTP Response Status Code, should be 200")
	public void getIndexResponseShouldHTTPStatusCodeOk200() {
		
		ResponseEntity<String> httpResponse = indexApiController.getIndex();
		assertThat(httpResponse.getStatusCode(), is(HttpStatus.OK));
	}
	
	
	@Test
	@DisplayName("Get index HTTP Response body, should be not null")
	public void getIndexResponseShouldNotNull() {
		
		ResponseEntity<String> httpResponse = indexApiController.getIndex();
		assertThat(httpResponse.getBody(), is(notNullValue()));
	}

}