package com.retailer.rewardsprogramapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.rewardsprogramapi.entity.Reward;
import com.retailer.rewardsprogramapi.service.RewardsService;

@RestController
public class RewardsController {

	@Autowired
	private RewardsService rewardsService;

	private Logger logger = LoggerFactory.getLogger(RewardsController.class);

	/**
	 * Controller method to update rewards
	 * 
	 * @return ResponseEntity
	 */
	@PostMapping(path = "/rewards")
	@Scheduled(cron = "@midnight")
	public ResponseEntity<HttpStatus> updateRewards() {

		logger.info("updateRewards() :: begin");

		// make the service call to update reward points
		boolean isSuccess = rewardsService.updateRewards();

		logger.info("updateRewards() :: end");

		return isSuccess ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
	}

	/**
	 * Controller method to update rewards
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(path = "/rewards")
	public ResponseEntity<Iterable<Reward>> getRewards() {

		logger.info("getRewards() :: begin");

		// make the service call to update reward points
		Iterable<Reward> rewards = rewardsService.getRewards();

		logger.info("getRewards() :: end");

		return ResponseEntity.ok().body(rewards);

	}

}
