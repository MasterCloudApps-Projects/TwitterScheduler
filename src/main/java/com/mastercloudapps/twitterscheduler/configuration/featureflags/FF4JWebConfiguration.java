package com.mastercloudapps.twitterscheduler.configuration.featureflags;

import org.ff4j.FF4j;
import org.ff4j.spring.boot.web.api.config.EnableFF4jSwagger;
import org.ff4j.web.FF4jDispatcherServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFF4jSwagger
@ConditionalOnClass({FF4jDispatcherServlet.class})
@AutoConfigureAfter(FF4JConfiguration.class)
public class FF4JWebConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(FF4JWebConfiguration.class);
	
	@Value("${ff4j.webconsole.url}")
    private String webConsoleUrl;
	
	@Bean
	@ConditionalOnMissingBean
	public FF4jDispatcherServlet defineFF4jServlet(FF4j ff4j) {
		
		logger.info("Initializing the web console servlet as ff4j as been found in contexts", webConsoleUrl);
		FF4jDispatcherServlet ff4jConsoleServlet = new FF4jDispatcherServlet();
		ff4jConsoleServlet.setFf4j(ff4j);
		return ff4jConsoleServlet;
	}

	@Bean
	@SuppressWarnings({"rawtypes","unchecked"})
	public ServletRegistrationBean registerFF4jServlet(FF4jDispatcherServlet ff4jDispatcherServlet) {
		
		logger.info("Exposing FF4j web console on '{}'", webConsoleUrl);
		return new ServletRegistrationBean(ff4jDispatcherServlet, webConsoleUrl + "/*");
	}
}