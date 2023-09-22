package com.retailer.rewardsprogramapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.retailer.rewardsprogramapi.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
