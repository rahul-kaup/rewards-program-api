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

import com.retailer.rewardsprogramapi.TransactionService;
import com.retailer.rewardsprogramapi.entity.Transaction;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	private Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@PostMapping(path = "/transaction")
	public ResponseEntity<HttpStatus> saveTransaction(@RequestBody List<Transaction> transactionsList) {

		logger.info("transactionsList = {}", transactionsList);

		boolean isSuccess = transactionService.addTransactions(transactionsList);

		return isSuccess ? ResponseEntity.ok(HttpStatus.OK) : ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
