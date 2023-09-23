package com.retailer.rewardsprogramapi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.rewardsprogramapi.entity.Transaction;
import com.retailer.rewardsprogramapi.repository.TransactionRepository;

@Service
public class TransactionService {

	Logger logger = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	private TransactionRepository transactionRepository;

	public boolean saveTransactions(List<Transaction> transactionsList) {

		try {
			transactionRepository.saveAll(transactionsList);

		} catch (Exception e) {
			logger.error("exception while saving transactions", e);
			return false;
		}

		return true;
	}

}
