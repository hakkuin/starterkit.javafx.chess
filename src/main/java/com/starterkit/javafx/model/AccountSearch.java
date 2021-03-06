package com.starterkit.javafx.model;

import java.util.ArrayList;
import java.util.List;

import com.starterkit.javafx.dataprovider.data.AccountVO;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * Data displayed on the account management screen.
 *
 */
public class AccountSearch {

	private final StringProperty login = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty surname = new SimpleStringProperty();
	private final ListProperty<AccountVO> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	private BooleanProperty deleteButtonProperty = new SimpleBooleanProperty();
	private BooleanProperty editButtonProperty = new SimpleBooleanProperty();
	
	private AccountVO selectedAccount;

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

	public final List<AccountVO> getResult() {
		return result.get();
	}

	public final void setResult(List<AccountVO> value) {
		result.setAll(value);
	}

	public ListProperty<AccountVO> resultProperty() {
		return result;
	}

	@Override
	public String toString() {
		return "AccountManagement [login=" + login + ", name=" + name + ", surname=" + surname + ", result="
				+ result + "]";
	}

	public AccountVO getSelectedAccount() {
		return selectedAccount;
	}

	public void setSelectedAccount(AccountVO selectedAccount) {
		this.selectedAccount = selectedAccount;
	}

	public BooleanProperty getDeleteButtonProperty() {
		return deleteButtonProperty;
	}

	public void setDeleteButtonProperty(BooleanProperty deleteButtonProperty) {
		this.deleteButtonProperty = deleteButtonProperty;
	}

	public BooleanProperty getEditButtonProperty() {
		return editButtonProperty;
	}

	public void setEditButtonProperty(BooleanProperty editButtonProperty) {
		this.editButtonProperty = editButtonProperty;
	}

}
