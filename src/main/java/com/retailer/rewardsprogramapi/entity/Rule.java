package com.retailer.rewardsprogramapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "rules_tbl")
@Data
public class Rule {
	@Id
	private Long ruleId;
	private Integer lowerBound;
	private Integer upperBound;
	private Integer rewardRate;

}
