package com.starterkit.javafx.dataprovider.impl;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.starterkit.javafx.dataprovider.DataProvider;
import com.starterkit.javafx.dataprovider.data.AccountVO;

/**
 * Provides data. Data is stored locally in this object. Additionally a call
 * delay is simulated.
 *
 * @author Leszek
 */
public class DataProviderImpl implements DataProvider {

	private static final Logger LOG = Logger.getLogger(DataProviderImpl.class);

	private static final String BASE_URL = "http://localhost:8090/user";

	public DataProviderImpl() {

	}

	@Override
	public Collection<AccountVO> findAccounts(String login, String name, String surname) {
		LOG.debug("Entering findAccounts()");
		LOG.debug("Argument: login:" + login);
		LOG.debug("Argument: name:" + name);
		LOG.debug("Argument: surname:" + surname);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<AccountVO>> profilesResponse;
		List<AccountVO> result = null;

		// TODO: move http adress to external location: config file / run
		// arguments / etc.
		// TODO: sprawdzic rzucane wyjatki przez RestTemplate
		// TODO: obsluzyc wyjatki i wyswietlic okno z bledem
		// TODO: test: bez postawionego serwera
		try {
			profilesResponse = restTemplate.exchange(
					BASE_URL + "/search?login=" + login + "&name=" + name + "&surname=" + surname, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<AccountVO>>() {
					});

			result = profilesResponse.getBody();
		} catch (Exception e) {
			// TODO: replace with LOG.error... / drugim parametrem erro jest
			// caly error - dodac go
			// TODO: przekazac wyjatek wyzej, utworzyc customowy wyjatek
			// null pointer - blad programisty, nie lapac
			LOG.debug("Exception occurred: " + e.getMessage());
		}

		LOG.debug("Returning: " + result);
		LOG.debug("Leaving findAccounts()");
		return result;
	}

	@Override
	public AccountVO findAccount(String login) {
		LOG.debug("Entering findAccount()");
		LOG.debug("Argument: login:" + login);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<AccountVO>> profilesResponse;
		List<AccountVO> result = null;

		try {
			profilesResponse = restTemplate.exchange(BASE_URL + "/search?login=" + login, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<AccountVO>>() {
					});

			result = profilesResponse.getBody();
		} catch (Exception e) {
			LOG.debug("Exception occurred: " + e.getMessage());
		}

		LOG.debug("Returning: " + result);
		LOG.debug("Leaving findAccounts()");
		return result.get(0);
	}

	@Override
	public void updateAccount(AccountVO accountToUpdate) {
		LOG.debug("Entering updateAccount()");
		LOG.debug("Argument: accountToUpdate:" + accountToUpdate);
		
		RestTemplate restTemplate = new RestTemplate();

		try {
			HttpEntity<AccountVO> entity = new HttpEntity<AccountVO>(accountToUpdate);
			restTemplate.exchange(BASE_URL, HttpMethod.PUT, entity, AccountVO.class);
			
		} catch (Exception e) {
			LOG.debug("Exception occurred: " + e.getMessage());
		}

		LOG.debug("Leaving updateAccount()");
	}

	@Override
	public void deleteAccount(long id) {
		LOG.debug("Entering deleteAccount()");
		LOG.debug("Argument: id:" + id);
		
		RestTemplate restTemplate = new RestTemplate();

		try {
			restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.DELETE, null, String.class);
			
		} catch (Exception e) {
			LOG.debug("Exception occurred: " + e.getMessage());
		}

		LOG.debug("Leaving deleteAccount()");
	}
}
