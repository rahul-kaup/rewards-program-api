package com.retailer.rewardsprogramapi.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class RewardId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long customerId;
	private Integer rewardYear;
	private Integer rewardMonth;
}
