package com.mastercloudapps.twitterscheduler.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mastercloudapps.twitterscheduler.application.usecase.CreatePendingTweetUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.DeletePendingTweetUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.FindAllPendingTweetUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.FindOnePendingTweetUseCase;
import com.mastercloudapps.twitterscheduler.application.usecase.PublishPendingTweetOnDemandUseCase;
import com.mastercloudapps.twitterscheduler.controller.exception.ImageNotAvailableException;
import com.mastercloudapps.twitterscheduler.controller.exception.MalformedImageUrlException;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingTweetRequest;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PendingTweetResponse;
import com.mastercloudapps.twitterscheduler.controller.pending.dto.PublishOnDemandResponse;
import com.mastercloudapps.twitterscheduler.controller.pending.mapper.CreatePendingTweetRequestMapper;
import com.mastercloudapps.twitterscheduler.controller.pending.mapper.DeletePendingTweetRequestMapper;
import com.mastercloudapps.twitterscheduler.controller.pending.mapper.FindOnePendingTweetRequestMapper;
import com.mastercloudapps.twitterscheduler.controller.pending.mapper.PendingTweetResponseMapper;
import com.mastercloudapps.twitterscheduler.controller.pending.mapper.PublishOnDemandResponseMapper;
import com.mastercloudapps.twitterscheduler.controller.pending.mapper.PublishPendingTweetOnDemandRequestMapper;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/pending")
@SecurityRequirement(name = "twitter-scheduler")
public class PendingApiController implements PendingApi {

	private static Logger logger = LoggerFactory.getLogger(PendingApiController.class);
	
	private final CreatePendingTweetRequestMapper createPendingTweetRequestMapper;

	private final PendingTweetResponseMapper pendingResponseMapper;

	private final CreatePendingTweetUseCase createPendingTweetUseCase;

	private final DeletePendingTweetUseCase deletePendingTweetUseCase;

	private final DeletePendingTweetRequestMapper deletePendingTweetRequestMapper;

	private final FindAllPendingTweetUseCase findAllPendingTweetUseCase;

	private final FindOnePendingTweetUseCase findOnePendingTweetUseCase;

	private final FindOnePendingTweetRequestMapper findOnePendingTweetRequestMapper;

	private final PublishOnDemandResponseMapper publishOnDemandResponseMapper;

	private final PublishPendingTweetOnDemandRequestMapper publishPendingTweetOnDemandRequestMapper;

	private final PublishPendingTweetOnDemandUseCase publishPendingTweetOnDemandUseCase;

	@Autowired
	public PendingApiController(
			final CreatePendingTweetRequestMapper createPendingTweetRequestMapper,
			final PendingTweetResponseMapper responseMapper,
			final CreatePendingTweetUseCase createPendingTweetUseCase,
			final DeletePendingTweetUseCase deletePendingTweetUseCase,
			final DeletePendingTweetRequestMapper deletePendingTweetRequestMapper,
			final FindAllPendingTweetUseCase findAllPendingTweetUseCase,
			final FindOnePendingTweetUseCase findOnePendingTweetUseCase,
			final FindOnePendingTweetRequestMapper findOnePendingTweetRequestMapper,
			final PublishOnDemandResponseMapper publishOnDemandResponseMapper,
			final PublishPendingTweetOnDemandRequestMapper publishPendingTweetOnDemandRequestMapper,
			final PublishPendingTweetOnDemandUseCase publishPendingTweetOnDemandUseCase) {

		this.createPendingTweetRequestMapper = createPendingTweetRequestMapper;
		this.pendingResponseMapper = responseMapper;
		this.createPendingTweetUseCase = createPendingTweetUseCase;
		this.deletePendingTweetUseCase = deletePendingTweetUseCase;
		this.deletePendingTweetRequestMapper = deletePendingTweetRequestMapper;
		this.findAllPendingTweetUseCase = findAllPendingTweetUseCase;
		this.findOnePendingTweetUseCase = findOnePendingTweetUseCase;
		this.findOnePendingTweetRequestMapper = findOnePendingTweetRequestMapper;
		this.publishOnDemandResponseMapper = publishOnDemandResponseMapper;
		this.publishPendingTweetOnDemandRequestMapper = publishPendingTweetOnDemandRequestMapper;
		this.publishPendingTweetOnDemandUseCase = publishPendingTweetOnDemandUseCase;
	}

	@GetMapping
	public Collection<PendingTweetResponse> getPendingTweets() {

		return findAllPendingTweetUseCase.findAll().stream()
				.map(pendingTweet -> pendingResponseMapper.mapResponse(pendingTweet))
				.collect(Collectors.toList());		
	}

	@GetMapping("/{id}")
	public ResponseEntity<PendingTweetResponse> getPendingTweetById(Long id) {

		final var pendingTweet = findOnePendingTweetUseCase.findOne(
				findOnePendingTweetRequestMapper.mapRequest(id));

		if(pendingTweet.isPresent()) {
			return new ResponseEntity<>(pendingResponseMapper.mapResponse(pendingTweet.get()),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<PendingTweetResponse> createPendingTweet(PendingTweetRequest request) {

		try {
			final var createPendingTweetRequest = createPendingTweetRequestMapper.mapRequest(request);
			
			final var createPendingTweetResponse = createPendingTweetUseCase.create(createPendingTweetRequest);

			return new ResponseEntity<>(pendingResponseMapper.mapResponse(createPendingTweetResponse),
					HttpStatus.CREATED);
		}
		catch(ImageNotAvailableException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		catch(MalformedImageUrlException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePendingTweet(Long id) {

		try {
			deletePendingTweetUseCase.delete(
					deletePendingTweetRequestMapper.mapRequest(id));
			return new ResponseEntity<>(null, HttpStatus.OK);

		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/{id}/publish")
	public ResponseEntity<PublishOnDemandResponse> publishOnDemand(Long id) {

		final var operation = publishPendingTweetOnDemandRequestMapper.mapRequest(id);

		final var useCaseResponse = publishPendingTweetOnDemandUseCase
				.publishImmediatly(operation);

		if (useCaseResponse.isPresent()) {
			final var controllerResponse = publishOnDemandResponseMapper
					.mapResponse(useCaseResponse.get());
			return new ResponseEntity<>(controllerResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

}