package com.mastercloudapps.twitterscheduler.controller.validator;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ImageAvailable {

	public boolean validate(String imageUrl) throws IOException {

		URL url = new URL(imageUrl);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		int responseCode = huc.getResponseCode();

		return responseCode == HttpStatus.OK.value();		
	}
}