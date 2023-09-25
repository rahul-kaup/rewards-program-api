package com.retailer.rewardsprogramapi.service;

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

import com.retailer.rewardsprogramapi.entity.Transaction;
import com.retailer.rewardsprogramapi.repository.TransactionRepository;
import com.retailer.rewardsprogramapi.util.TestUtil;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

	@Mock
	TransactionRepository transactionRepository;

	@InjectMocks
	TransactionService transactionService;

	TestUtil testUtil = new TestUtil();

	/**
	 * Test successfull service response
	 * 
	 * @throws IOException
	 */
	@Test
	void testSaveTransactionsSuccess() throws IOException {

		List<Transaction> transactions = testUtil.getTransactions("transactions-valid");

		// mock db response
		when(transactionRepository.saveAll(transactions)).thenReturn(transactions);

		boolean isSuccess = transactionService.saveTransactions(transactions);

		assertTrue(isSuccess, "is save successful?");
	}

	/**
	 * Test failure service response on exception
	 * 
	 * @throws IOException
	 */
	@Test
	void testSaveTransactionsFailure() throws IOException {

		List<Transaction> transactions = testUtil.getTransactions("transactions-valid");

		// mock db response
		when(transactionRepository.saveAll(transactions)).thenThrow(new RuntimeException());

		boolean isSuccess = transactionService.saveTransactions(transactions);

		assertFalse(isSuccess, "is save successful?");
	}

}
