package com.retailer.rewardsprogramapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.rewardsprogramapi.entity.Transaction;
import com.retailer.rewardsprogramapi.service.TransactionService;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	private Logger logger = LoggerFactory.getLogger(TransactionController.class);

	/**
	 * Controller method for adding and updating transactions
	 * 
	 * @param transactionsList
	 * @return ResponseEntity
	 */
	@PostMapping(path = "/transactions")
	public ResponseEntity<HttpStatus> saveTransactions(@RequestBody List<Transaction> transactionsList) {

		logger.info("saveTransactions() :: transactionsList = {}", transactionsList);

		// make the service call to save transactions
		boolean isSuccess = transactionService.saveTransactions(transactionsList);

		logger.info("saveTransactions() :: isSuccess = {}", isSuccess);

		return isSuccess ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
	}

}
