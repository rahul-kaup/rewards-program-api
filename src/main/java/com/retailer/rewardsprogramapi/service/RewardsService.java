package com.retailer.rewardsprogramapi.service;

import java.util.Collection;
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
public class RewardsService {

	Logger logger = LoggerFactory.getLogger(RewardsService.class);

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private RulesRepository rulesRepository;

	@Autowired
	private RewardRepository rewardRepository;

	public boolean updateRewards() {

		try {

			// fetch transactions and rules from db
			List<Rule> rules = (List<Rule>) rulesRepository.findAll();
			List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
			if (transactions.isEmpty() || rules.isEmpty()) {
				logger.warn("transactions or rules not available for reward computation");
				return false;
			}

			// compute consolidated rewards
			Collection<Reward> rewards = computeRewards(transactions, rules);

			// save rewards to db
			rewardRepository.deleteAll();
			rewardRepository.saveAll(rewards);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}

		return true;
	}

	public Collection<Reward> computeRewards(List<Transaction> transactions, List<Rule> rules) {

		Map<RewardKey, Reward> rewardsMap = new HashMap<>();

		// iterate transactions
		for (Transaction transaction : transactions) {
			logger.info("transaction = {}", transaction);
			int points = 0;

			// iterate rules
			for (Rule rule : rules) {

				// compute reward points for each rule
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

		return rewardsMap.values();
	}

	public Iterable<Reward> getRewards() {

		return rewardRepository.findAll();
	}

}
