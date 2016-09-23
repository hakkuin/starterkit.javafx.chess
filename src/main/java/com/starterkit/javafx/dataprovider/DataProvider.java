package com.starterkit.javafx.dataprovider;

import java.util.Collection;

import com.starterkit.javafx.dataprovider.data.AccountVO;
import com.starterkit.javafx.dataprovider.impl.DataProviderImpl;

/**
 * Provides data.
 *
 * @author Leszek
 */
public interface DataProvider {

	/**
	 * Instance of this interface.
	 */
	DataProvider INSTANCE = new DataProviderImpl();

	/**
	 * @param login
	 * 				string containing user id
	 * @param name
	 * 				string containing first name
	 * @param surname
	 * 				string containing last name
	 * @return collection of accounts matching given criteria
	 */
	Collection<AccountVO> findAccounts(String login, String name, String surname);

	/**
	 * @param login
	 * 				string containing user login
	 * @return account matching given login
	 */
	AccountVO findAccount(String login);
	
	/**
	 * updates given account in database
	 * 
	 * @param account
	 */
	void updateAccount(AccountVO account);
	
	/**
	 * deletes selected account from database
	 * 
	 * @param id
	 */
	void deleteAccount(long id);
}
