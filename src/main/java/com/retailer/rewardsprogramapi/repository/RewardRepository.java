package com.retailer.rewardsprogramapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.retailer.rewardsprogramapi.entity.Reward;

public interface RewardRepository extends CrudRepository<Reward, Long> {

}
