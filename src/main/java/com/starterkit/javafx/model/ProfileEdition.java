package com.starterkit.javafx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Data displayed on the account management screen.
 *
 */
public class ProfileEdition {

	private final StringProperty login = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty surname = new SimpleStringProperty();
	private final StringProperty email = new SimpleStringProperty();
	private final StringProperty password = new SimpleStringProperty();
	private final StringProperty aboutMe = new SimpleStringProperty();
	private final StringProperty lifeMotto = new SimpleStringProperty();
	
	private long profileId;

	public final String getLogin() {
		return login.get();
	}

	public final void setLogin(String value) {
		login.set(value);
	}

	public StringProperty loginProperty() {
		return login;
	}

	public final String getName() {
		return name.get();
	}

	public final void setName(String value) {
		name.set(value);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public final String getSurname() {
		return surname.get();
	}

	public final void setSurname(String value) {
		surname.set(value);
	}

	public StringProperty surnameProperty() {
		return surname;
	}

	public final String getEmail() {
		return email.get();
	}

	public final void setEmail(String value) {
		email.set(value);
	}

	public StringProperty emailProperty() {
		return email;
	}

	public final String getPassword() {
		return password.get();
	}

	public final void setPassword(String value) {
		password.set(value);
	}

	public StringProperty passwordProperty() {
		return password;
	}

	public final String getAboutMe() {
		return aboutMe.get();
	}

	public final void setAboutMe(String value) {
		aboutMe.set(value);
	}

	public StringProperty aboutMeProperty() {
		return aboutMe;
	}

	public final String getLifeMotto() {
		return lifeMotto.get();
	}

	public final void setLifeMotto(String value) {
		lifeMotto.set(value);
	}

	public StringProperty lifeMottoProperty() {
		return lifeMotto;
	}

	public long getProfileId() {
		return profileId;
	}

	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}
	
}
