package com.retailer.rewardsprogramapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.retailer.rewardsprogramapi.entity.Reward;
import com.retailer.rewardsprogramapi.entity.Rule;
import com.retailer.rewardsprogramapi.entity.Transaction;
import com.retailer.rewardsprogramapi.repository.RewardRepository;
import com.retailer.rewardsprogramapi.repository.RulesRepository;
import com.retailer.rewardsprogramapi.repository.TransactionRepository;
import com.retailer.rewardsprogramapi.util.TestUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RewardsComputationControllerIntegrationTest {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	RulesRepository rulesRepository;

	@Autowired
	RewardRepository rewardRepository;

	@Autowired
	TestUtil testUtil;

	/**
	 * 
	 * 
	 * @throws IOException
	 */
	@Test
	void testComputeAndSaveRewardsSuccess() throws IOException {

		// verify db is empty
		assertEquals(0, transactionRepository.count());
		assertEquals(0, rulesRepository.count());
		assertEquals(0, rewardRepository.count());

		// add transactions from json to db
		List<Transaction> transactions = testUtil.getTransactions("transactions-valid");
		transactionRepository.saveAll(transactions);

		// add rules from json to db
		List<Rule> rules = testUtil.getRules("rules");
		rulesRepository.saveAll(rules);

		// make api call to compute rewards
		ResponseEntity<HttpStatus> response = testRestTemplate.postForEntity("/rewards/compute", null,
				HttpStatus.class);

		// validate http response
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// validate rewards in db
		List<Reward> expectedRewards = testUtil.getRewards("rewards");
		List<Reward> actualRewards = (List<Reward>) rewardRepository.findAll();
		assertEquals(expectedRewards, actualRewards);

	}

	/**
	 * 
	 * 
	 * @throws IOException
	 */
	@Test
	void testComputeAndSaveRewardsFailure() throws IOException {

		// verify db is empty
		assertEquals(0, transactionRepository.count());
		assertEquals(0, rulesRepository.count());

		// make api call to compute rewards without adding transactions or rules
		ResponseEntity<HttpStatus> response = testRestTemplate.postForEntity("/rewards/compute", null,
				HttpStatus.class);

		// validate http response
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@BeforeEach
	void beforeEach() {
		// clear db
		transactionRepository.deleteAll();
		rulesRepository.deleteAll();
		rewardRepository.deleteAll();
	}

}
