package com.retailer.rewardsprogramapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "transactions_tbl")
@Data
public class Transaction {
	@Id
	private Long transactionId;
	private Long customerId;
	private Integer transactionDate;
	private Float transactionAmount;

}
