package com.retailer.rewardsprogramapi.bean;

import lombok.Data;

@Data
public class RewardKey {

	private final Long customerId;
	private final Integer year;
	private final Integer month;

}
