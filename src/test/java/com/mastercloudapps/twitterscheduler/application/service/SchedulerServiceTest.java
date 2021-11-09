package com.mastercloudapps.twitterscheduler.application.service;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.togglz.core.manager.FeatureManager;

import com.mastercloudapps.twitterscheduler.application.model.scheduler.SchedulerConfiguration;
import com.mastercloudapps.twitterscheduler.application.model.scheduler.SchedulerInfo;
import com.mastercloudapps.twitterscheduler.configuration.featureflags.Features;
import com.mastercloudapps.twitterscheduler.mocks.SchedulerInfoData;

@ExtendWith(MockitoExtension.class)

public class SchedulerServiceTest {

	private SchedulerService service;

	@Mock
	private FeatureManager featureManager;
	
	@Mock
	private SchedulerConfiguration schedulerConfiguration;

	private SchedulerInfo activeSchedulerInfo;

	private SchedulerInfo inactiveSchedulerInfo;

	@BeforeEach
	public void beforeEach() {

		this.service = new SchedulerService(featureManager, schedulerConfiguration);

		this.activeSchedulerInfo = SchedulerInfoData.ACTIVE.create();

		this.inactiveSchedulerInfo = SchedulerInfoData.INACTIVE.create();
	}

	@Test
	@DisplayName("Test get scheduler info when active")
	void givenGetScheduleStatusValidRequest_whenActive_expectScheduledInfoActive() {

		when(featureManager.isActive(Features.SCHEDULER)).thenReturn(true);
		when(schedulerConfiguration.getFixedRate()).thenReturn("180000");
		when(schedulerConfiguration.getInitialDelay()).thenReturn("60000");

		final var info = service.getInfo();

		assertThat(info, is(notNullValue()));
		assertThat(info.get(), instanceOf(SchedulerInfo.class));
		assertThat(info.get().isActive(), is(true));
		assertEquals(info.get().getFixedRate(), activeSchedulerInfo.getFixedRate());
		assertEquals(info.get().getInitialDelay(), activeSchedulerInfo.getInitialDelay());
	}	

	@Test
	@DisplayName("Test get scheduler status when inactive")
	void givenGetScheduleStatusValidRequest_whenInactive_expectScheduledInfoActive() {

		when(featureManager.isActive(Features.SCHEDULER)).thenReturn(false);
		when(schedulerConfiguration.getFixedRate()).thenReturn("180000");
		when(schedulerConfiguration.getInitialDelay()).thenReturn("60000");

		final var info = service.getInfo();

		assertThat(info, is(notNullValue()));
		assertThat(info.get(), instanceOf(SchedulerInfo.class));
		assertThat(info.get().isActive(), is(false));
		assertEquals(info.get().getFixedRate(), inactiveSchedulerInfo.getFixedRate());
		assertEquals(info.get().getInitialDelay(), inactiveSchedulerInfo.getInitialDelay());
	}


}
