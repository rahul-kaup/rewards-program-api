package com.retailer.rewardsprogramapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "rewards_tbl")
@IdClass(RewardId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reward {

	@Id
	private Long customerId;

	@Id
	private Integer rewardYear;

	@Id
	private Integer rewardMonth;

	private Integer points;

}
