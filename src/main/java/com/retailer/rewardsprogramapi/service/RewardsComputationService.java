package com.retailer.rewardsprogramapi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.rewardsprogramapi.entity.Rule;
import com.retailer.rewardsprogramapi.entity.Transaction;
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

			// read rules
			List<Rule> rules = (List<Rule>) rulesRepository.findAll();
			logger.info("rules = {}", rules);

			// read transactions
			List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
			logger.info("transactions = {}", transactions);

			// iterate transactions
			for (Transaction transaction : transactions) {

				int reward = 0;

				// iterate rules
				for (Rule rule : rules) {

					// compute reward based on each rule
					if (transaction.getTransactionAmount().intValue() >= rule.getLowerBound()) {

						if (rule.getUpperBound() != null
								&& transaction.getTransactionAmount().intValue() >= rule.getUpperBound()) {
							reward += (rule.getUpperBound() - rule.getLowerBound() + 1) * rule.getRewardRate();
						} else {
							reward += (transaction.getTransactionAmount().intValue() - rule.getLowerBound() + 1)
									* rule.getRewardRate();
						}
					}
				}
				logger.info("reward = {}", reward);
			}

			// write rewards

		} catch (Exception e) {
			logger.error("exception while saving transactions", e);
			return false;
		}

		return true;
	}

}
