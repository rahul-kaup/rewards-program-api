package com.retailer.rewardsprogramapi.util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retailer.rewardsprogramapi.entity.Reward;
import com.retailer.rewardsprogramapi.entity.Rule;
import com.retailer.rewardsprogramapi.entity.Transaction;

@Component
public class TestUtil {

	ObjectMapper jsonMapper = new ObjectMapper();

	/**
	 * Reads json test data from test resources
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public String getJson(String fileName) throws IOException {
		// read json from test data file
		return Files.readString(new ClassPathResource("data/" + fileName + ".json").getFile().toPath());

	}

	/**
	 * Returns test transactions from json test data
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public List<Transaction> getTransactions(String fileName) throws IOException {

		// convert to pojo
		List<Transaction> transactionsList = jsonMapper.readValue(getJson(fileName),
				new TypeReference<List<Transaction>>() {
				});

		return transactionsList;

	}

	/**
	 * Returns test transactions from json test data
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public List<Rule> getRules(String fileName) throws IOException {

		// convert to pojo
		List<Rule> rulesList = jsonMapper.readValue(getJson(fileName), new TypeReference<List<Rule>>() {
		});

		return rulesList;

	}

	public List<Reward> getRewards(String fileName) throws JsonMappingException, JsonProcessingException, IOException {
		// convert to pojo
		List<Reward> rewardsList = jsonMapper.readValue(getJson(fileName), new TypeReference<List<Reward>>() {
		});
		return rewardsList;
	}
}
