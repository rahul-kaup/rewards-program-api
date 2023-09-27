package com.retailer.rewardsprogramapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retailer.rewardsprogramapi.entity.Reward;
import com.retailer.rewardsprogramapi.entity.Rule;
import com.retailer.rewardsprogramapi.entity.Transaction;
import com.retailer.rewardsprogramapi.repository.RewardRepository;
import com.retailer.rewardsprogramapi.repository.RuleRepository;
import com.retailer.rewardsprogramapi.repository.TransactionRepository;
import com.retailer.rewardsprogramapi.util.TestUtil;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

	@Mock
	TransactionRepository transactionRepository;

	@Mock
	RuleRepository ruleRepository;

	@Mock
	RewardRepository rewardRepository;

	@InjectMocks
	RewardService rewardsService;

	TestUtil testUtil = new TestUtil();

	/**
	 * Test successful run of rewards update
	 * 
	 * @throws IOException
	 */
	@Test
	void testUpdateRewardsSuccess() throws IOException {

		// mock db response
		when(transactionRepository.findAll()).thenReturn(testUtil.getTransactions("transactions-valid"));
		when(ruleRepository.findAll()).thenReturn(testUtil.getRules("rules"));
		
		boolean isSuccess = rewardsService.updateRewards();

		assertTrue(isSuccess, "is update successful?");
	}

	/**
	 * Test failure when no rules present
	 * 
	 * @throws IOException
	 */
	@Test
	void testUpdateRewardsFailureNoRules() throws IOException {

		// mock db response
		when(transactionRepository.findAll()).thenReturn(testUtil.getTransactions("transactions-valid"));
		
		boolean isSuccess = rewardsService.updateRewards();
		
		assertFalse(isSuccess, "is update successful?");
	}

	/**
	 * Test failure when no transactions present
	 * 
	 * @throws IOException
	 */
	@Test
	void testUpdateRewardsFailureNoTransactions() throws IOException {

		// mock db response
		when(ruleRepository.findAll()).thenReturn(testUtil.getRules("rules"));
		
		boolean isSuccess = rewardsService.updateRewards();
		
		assertFalse(isSuccess, "is update successful?");
	}

	/**
	 * Test failure on exception during save
	 * 
	 * @throws IOException
	 */
	@Test
	void testUpdateRewardsFailureOnException() throws IOException {

		// mock db response
		when(transactionRepository.findAll()).thenReturn(testUtil.getTransactions("transactions-valid"));
		when(ruleRepository.findAll()).thenReturn(testUtil.getRules("rules"));
		
		// mock save failure
		when(rewardRepository.saveAll(testUtil.getRewards("rewards"))).thenThrow(new RuntimeException());
		
		boolean isSuccess = rewardsService.updateRewards();
		
		assertFalse(isSuccess, "is update successful?");
	}

	@Test
	void testComputeRewards() throws IOException {

		// get test data
		List<Reward> expectedRewards = testUtil.getRewards("rewards");
		List<Transaction> transactions = testUtil.getTransactions("transactions-valid");
		List<Rule> rules = testUtil.getRules("rules");

		// make method call to compute rewards
		List<Reward> actualRewards = rewardsService.computeRewards(transactions, rules);

		// validate computed rewards
		assertEquals(expectedRewards, actualRewards);

	}

}
