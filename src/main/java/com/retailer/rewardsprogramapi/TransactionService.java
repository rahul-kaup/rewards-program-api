package com.retailer.rewardsprogramapi;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.rewardsprogramapi.controller.TransactionController;
import com.retailer.rewardsprogramapi.entity.Transaction;
import com.retailer.rewardsprogramapi.repository.TransactionRepository;

@Service
public class TransactionService {

	Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	private TransactionRepository transactionRepository;

	public boolean addTransactions(List<Transaction> transactionsList) {

		try {
			transactionRepository.saveAll(transactionsList);

		} catch (Exception e) {
			logger.error("exception while adding transactions", e);
			return false;
		}

		return true;
	}

}
