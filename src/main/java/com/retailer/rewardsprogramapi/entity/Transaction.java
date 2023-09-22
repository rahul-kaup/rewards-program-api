package com.retailer.rewardsprogramapi.entity;

import java.math.BigInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "transactions_tbl")
@Data
public class Transaction {
	@Id
	private BigInteger transactionId;
	private BigInteger customerId;
	private Integer transactionDate;
	private Float transactionAmount;

}
