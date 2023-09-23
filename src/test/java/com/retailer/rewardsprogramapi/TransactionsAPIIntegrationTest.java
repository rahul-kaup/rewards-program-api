package com.retailer.rewardsprogramapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retailer.rewardsprogramapi.controller.TransactionController;
import com.retailer.rewardsprogramapi.entity.Transaction;
import com.retailer.rewardsprogramapi.repository.TransactionRepository;

@SpringBootTest
class TransactionsAPIIntegrationTest {

	@Autowired
	TransactionController transactionController;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	ObjectMapper jsonMapper;

	@Test
	void contextLoads() {
		assertThat(transactionController).isNotNull();
	}

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
		List<Transaction> expectedTransactions = getTransactions("transactions");

		// make controller call to add transactions to db
		ResponseEntity<HttpStatus> response = transactionController.saveTransactions(expectedTransactions);

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
		List<Transaction> expectedTransactions = getTransactions("transactions");

		// make controller call to add transactions to db
		transactionController.saveTransactions(expectedTransactions);

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
		ResponseEntity<HttpStatus> response = transactionController.saveTransactions(List.of(expectedTransaction));

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
		List<Transaction> expectedTransactions = getTransactions("transactions-invalid");

		// make controller call to add transactions to db
		ResponseEntity<HttpStatus> response = transactionController.saveTransactions(expectedTransactions);

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

	private List<Transaction> getTransactions(String fileName) throws IOException {
		// read transactions json from test data file
		String transactionsJson = Files
				.readString(new ClassPathResource("data/" + fileName + ".json").getFile().toPath());

		// convert to pojo
		List<Transaction> transactionsList = jsonMapper.readValue(transactionsJson,
				new TypeReference<List<Transaction>>() {
				});

		return transactionsList;

	}

}
