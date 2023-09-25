package com.retailer.rewardsprogramapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.retailer.rewardsprogramapi.entity.Rule;

public interface RulesRepository extends CrudRepository<Rule, Long> {

}
