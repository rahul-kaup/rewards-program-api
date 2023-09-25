package com.retailer.rewardsprogramapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.rewardsprogramapi.service.RewardsComputationService;

@RestController
public class RewardsComputationController {

	@Autowired
	private RewardsComputationService rewardsComputationService;

	private Logger logger = LoggerFactory.getLogger(RewardsComputationController.class);

	/**
	 * Controller method computing rewards
	 * 
	 * @param transactionsList
	 * @return ResponseEntity
	 */
	@PostMapping(path = "/rewards/compute")
	@Scheduled(cron = "@midnight")
	public ResponseEntity<HttpStatus> computeRewards() {

		logger.info("computeRewards() :: begin");

		// make the service call to save transactions
		boolean isSuccess = rewardsComputationService.computeRewards();

		logger.info("computeRewards() :: end");

		return isSuccess ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
	}

}
