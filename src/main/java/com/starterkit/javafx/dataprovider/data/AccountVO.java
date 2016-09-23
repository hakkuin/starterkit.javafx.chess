package com.starterkit.javafx.dataprovider.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Account data.
 *
 * @author Pawel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountVO {

	private long id;
	private String login;
	private String name;
	private String surname;
	private String email;
	private String password;
	private String aboutMe;
	private String lifeMotto;
	
	public AccountVO() {
	}

	public AccountVO(long id, String login, String name, String surname, String email, String password, String aboutMe,
			String lifeMotto) {
		this.id = id;
		this.login = login;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.aboutMe = aboutMe;
		this.lifeMotto = lifeMotto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getLifeMotto() {
		return lifeMotto;
	}

	public void setLifeMotto(String lifeMotto) {
		this.lifeMotto = lifeMotto;
	}
	
}
