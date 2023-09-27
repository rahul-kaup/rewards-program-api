package com.retailer.rewardsprogramapi.vo;

import java.util.SortedMap;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RewardVO {
	SortedMap<String, Integer> points;
	Integer total;

}
