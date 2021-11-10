package com.mastercloudapps.twitterscheduler.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mastercloudapps.twitterscheduler.application.model.scheduler.SchedulerInfo;
import com.mastercloudapps.twitterscheduler.application.usecase.GetSchedulerInfoUseCase;
import com.mastercloudapps.twitterscheduler.mocks.SchedulerInfoData;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("SchedulerApiController Unit tests using mocks")
class SchedulerApiControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private GetSchedulerInfoUseCase getSchedulerInfoUseCase;
	
	private SchedulerInfo activeSchedulerInfo;

	private SchedulerInfo inactiveSchedulerInfo;
	
	@BeforeEach
	public void beforeEach() {

		this.activeSchedulerInfo = SchedulerInfoData.ACTIVE.create();

		this.inactiveSchedulerInfo = SchedulerInfoData.INACTIVE.create();
	}
	
	@Test
	@DisplayName("Get scheduler info when active, expect configuration with active true")
	void getSchedulerStatusWhenActive() throws Exception {
		
		when(getSchedulerInfoUseCase.getInfo()).thenReturn(Optional.of(activeSchedulerInfo));
		
		mvc.perform(
	    		get("/api/scheduler/info")
	    		.contentType(MediaType.APPLICATION_JSON)
	    	)
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.active", equalTo(true)));
	}
	
	@Test
	@DisplayName("Get scheduler status when not active, expect configuration with active false")
	void getSchedulerStatusWhenNotActive() throws Exception {
		
		when(getSchedulerInfoUseCase.getInfo()).thenReturn(Optional.of(inactiveSchedulerInfo));
		
		mvc.perform(
	    		get("/api/scheduler/info")
	    		.contentType(MediaType.APPLICATION_JSON)
	    	)
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.active", equalTo(false)));
	}
	
}
