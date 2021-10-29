package com.mastercloudapps.twitterscheduler.configuration.featureflags;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.togglz.core.Feature;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.file.FileBasedStateRepository;
import org.togglz.core.user.FeatureUser;
import org.togglz.core.user.SimpleFeatureUser;
import org.togglz.core.user.UserProvider;

@Configuration
public class TogglzConfiguration implements TogglzConfig {

	@Value("${openapi.credentials.user}")
	private String user;
	
	@Override
	public Class<? extends Feature> getFeatureClass() {
		return Features.class;
	}

	@Override
	public StateRepository getStateRepository() {
		return new FileBasedStateRepository(new File("/tmp/features.properties"));
	}

	@Bean
	public UserProvider getUserProvider() {

		return new UserProvider() {
			@Override
			public FeatureUser getCurrentUser() {
				String loggedUser = SecurityContextHolder
						.getContext()
						.getAuthentication()
						.getName();
				boolean isAdmin = loggedUser.equalsIgnoreCase(user) ? true : false;
				return new SimpleFeatureUser(loggedUser, isAdmin);
			}
		};
	}

}
