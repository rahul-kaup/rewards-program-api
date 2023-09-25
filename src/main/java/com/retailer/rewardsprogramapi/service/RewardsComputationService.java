package com.retailer.rewardsprogramapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.rewardsprogramapi.repository.RulesRepository;
import com.retailer.rewardsprogramapi.repository.TransactionRepository;

@Service
public class RewardsComputationService {

	Logger logger = LoggerFactory.getLogger(RewardsComputationService.class);

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private RulesRepository rulesRepository;

	public boolean computeRewards() {

		try {
			
			
		} catch (Exception e) {
			logger.error("exception while saving transactions", e);
			return false;
		}

		return true;
	}

}
