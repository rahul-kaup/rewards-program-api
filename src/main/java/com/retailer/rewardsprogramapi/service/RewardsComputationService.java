package com.retailer.rewardsprogramapi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.rewardsprogramapi.bean.RewardKey;
import com.retailer.rewardsprogramapi.entity.Reward;
import com.retailer.rewardsprogramapi.entity.Rule;
import com.retailer.rewardsprogramapi.entity.Transaction;
import com.retailer.rewardsprogramapi.repository.RewardRepository;
import com.retailer.rewardsprogramapi.repository.RulesRepository;
import com.retailer.rewardsprogramapi.repository.TransactionRepository;

@Service
public class RewardsComputationService {

	Logger logger = LoggerFactory.getLogger(RewardsComputationService.class);

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private RulesRepository rulesRepository;

	@Autowired
	private RewardRepository rewardRepository;

	public boolean computeRewards() {

		try {

			// read rules
			List<Rule> rules = (List<Rule>) rulesRepository.findAll();
			logger.info("rules = {}", rules);

			// read transactions
			List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();

			Map<RewardKey, Reward> rewardsMap = new HashMap<>();

			// iterate transactions
			for (Transaction transaction : transactions) {

				logger.info("transaction = {}", transaction);

				int points = 0;

				// iterate rules
				for (Rule rule : rules) {

					// compute reward based on each rule
					if (transaction.getTransactionAmount().intValue() >= rule.getLowerBound()) {

						if (rule.getUpperBound() != null
								&& transaction.getTransactionAmount().intValue() >= rule.getUpperBound()) {
							points += (rule.getUpperBound() - rule.getLowerBound() + 1) * rule.getRewardRate();
						} else {
							points += (transaction.getTransactionAmount().intValue() - rule.getLowerBound() + 1)
									* rule.getRewardRate();
						}
					}
				}
				logger.info("points = {}", points);

				// consolidate rewards for every customer every month
				if (points > 0) {
					Integer year = transaction.getTransactionDate() / 10000;
					Integer month = transaction.getTransactionDate() % 10000 / 100;
					RewardKey rewardKey = new RewardKey(transaction.getCustomerId(), year, month);

					if (rewardsMap.containsKey(rewardKey)) {
						rewardsMap.get(rewardKey).setPoints(rewardsMap.get(rewardKey).getPoints() + points);
					} else {
						rewardsMap.put(rewardKey, new Reward(transaction.getCustomerId(), year, month, points));
					}
				}
			}

			// write rewards
			rewardRepository.saveAll(rewardsMap.values());

		} catch (Exception e) {
			logger.error("exception while saving transactions", e);
			return false;
		}

		return true;
	}

}
