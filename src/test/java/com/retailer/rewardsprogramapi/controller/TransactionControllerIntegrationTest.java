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

import com.retailer.rewardsprogramapi.entity.Transaction;
import com.retailer.rewardsprogramapi.repository.TransactionRepository;
import com.retailer.rewardsprogramapi.util.TestUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerIntegrationTest {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	TestUtil testUtil;

	/**
	 * Tests if list of transactions are successfully saved to the database
	 * 
	 * @throws IOException
	 */
	@Test
	void testAddTransactionsSuccess() throws IOException {

		// verify db is empty
		assertEquals(0, transactionRepository.count());

		// get transactions from json
		List<Transaction> expectedTransactions = testUtil.getTransactions("transactions-valid");

		// make controller call to add transactions to db
		ResponseEntity<HttpStatus> response = testRestTemplate.postForEntity("/transactions", expectedTransactions,
				HttpStatus.class);

		// fetch transactions from db
		List<Transaction> actualTransactions = (List<Transaction>) transactionRepository.findAll();

		// validate transactions from db
		assertEquals(3, transactionRepository.count());
		assertEquals(expectedTransactions, actualTransactions);

		// validate http response
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	/**
	 * Tests if a transaction is successfully updated in the database
	 * 
	 * @throws IOException
	 */
	@Test
	void testUpdateTransactionSuccess() throws IOException {

		// verify db is empty
		assertEquals(0, transactionRepository.count());

		// get transactions from json
		List<Transaction> expectedTransactions = testUtil.getTransactions("transactions-valid");

		// make controller call to add transactions to db
		ResponseEntity<HttpStatus> response = testRestTemplate.postForEntity("/transactions", expectedTransactions,
				HttpStatus.class);

		// fetch transactions from db
		List<Transaction> actualTransactions = (List<Transaction>) transactionRepository.findAll();

		// validate transactions from db
		assertEquals(3, transactionRepository.count());
		assertEquals(expectedTransactions, actualTransactions);

		// create transaction to update
		Transaction expectedTransaction = new Transaction();
		expectedTransaction.setTransactionId(Long.valueOf(1));
		expectedTransaction.setCustomerId(Long.valueOf(3));
		expectedTransaction.setTransactionDate(20230923);
		expectedTransaction.setTransactionAmount(199.99F);

		// make controller call to update transaction
		response = testRestTemplate.postForEntity("/transactions", List.of(expectedTransaction), HttpStatus.class);

		// validate transactions from db
		assertEquals(3, transactionRepository.count());
		Transaction actualTransaction = transactionRepository.findById(Long.valueOf(1)).get();
		assertEquals(expectedTransaction, actualTransaction);

		// validate http response
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	/**
	 * Tests if list of transactions are successfully saved to the database
	 * 
	 * @throws IOException
	 */
	@Test
	void testSaveTransactionsFailure() throws IOException {

		// verify db is empty
		assertEquals(0, transactionRepository.count());

		// get transactions from json
		List<Transaction> expectedTransactions = testUtil.getTransactions("transactions-invalid");

		// make controller call to add transactions to db
		ResponseEntity<HttpStatus> response = testRestTemplate.postForEntity("/transactions", expectedTransactions,
				HttpStatus.class);

		// validate transactions from db
		assertEquals(0, transactionRepository.count());

		// validate http response
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@BeforeEach
	void beforeEach() {
		// clear db
		transactionRepository.deleteAll();
	}

	

}
