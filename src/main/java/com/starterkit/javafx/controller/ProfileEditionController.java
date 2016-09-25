package com.starterkit.javafx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.starterkit.javafx.dataprovider.DataProvider;
import com.starterkit.javafx.dataprovider.data.AccountVO;
import com.starterkit.javafx.model.ProfileEdition;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileEditionController {

	private static final Logger LOG = Logger.getLogger(ProfileEditionController.class);

	/**
	 * Resource bundle loaded with this controller. JavaFX injects a resource
	 * bundle specified in {@link FXMLLoader#load(URL, ResourceBundle)} call.
	 * <p>
	 * NOTE: The variable name must be {@code resources}.
	 * </p>
	 */
	@FXML
	private ResourceBundle resources;

	/**
	 * URL of the loaded FXML file. JavaFX injects an URL specified in
	 * {@link FXMLLoader#load(URL, ResourceBundle)} call.
	 * <p>
	 * NOTE: The variable name must be {@code location}.
	 * </p>
	 */
	@FXML
	private URL location;

	/**
	 * JavaFX injects an object defined in FXML with the same "fx:id" as the
	 * variable name.
	 */
	@FXML
	private TextField nameField;

	@FXML
	private TextField surnameField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField passwordField;

	@FXML
	private TextField aboutMeField;

	@FXML
	private TextField lifeMottoField;

	@FXML
	private Label loginLabel;

	@FXML
	private Button saveButton;

	@FXML
	private Button cancelButton;
	
	private final DataProvider dataProvider = DataProvider.INSTANCE;

	private final ProfileEdition model = new ProfileEdition();
	
	private AccountSearchController accountSearchController;

	/**
	 * The JavaFX runtime instantiates this controller.
	 * <p>
	 * The @FXML annotated fields are not yet initialized at this point.
	 * </p>
	 */
	public ProfileEditionController() {
		LOG.debug("Constructor: nameField = " + nameField);
		LOG.debug("Constructor: surnameField = " + surnameField);
		LOG.debug("Constructor: emailField = " + emailField);
		LOG.debug("Constructor: passwordField = " + passwordField);
		LOG.debug("Constructor: aboutMeField = " + aboutMeField);
		LOG.debug("Constructor: lifeMottoField = " + lifeMottoField);
	}

	/**
	 * The JavaFX runtime calls this method after loading the FXML file.
	 * <p>
	 * The @FXML annotated fields are initialized at this point.
	 * </p>
	 * <p>
	 * NOTE: The method name must be {@code initialize}.
	 * </p>
	 */
	@FXML
	private void initialize() {
		LOG.debug("initialize(): loginLabel = " + loginLabel);
		LOG.debug("initialize(): nameField = " + nameField);
		LOG.debug("initialize(): surnameField = " + surnameField);
		LOG.debug("initialize(): emailField = " + emailField);
		LOG.debug("initialize(): passwordField = " + passwordField);
		LOG.debug("initialize(): aboutMeField = " + aboutMeField);
		LOG.debug("initialize(): lifeMottoField = " + lifeMottoField);

		loginLabel.textProperty().bindBidirectional(model.loginProperty());
		nameField.textProperty().bindBidirectional(model.nameProperty());
		surnameField.textProperty().bindBidirectional(model.surnameProperty());
		emailField.textProperty().bindBidirectional(model.emailProperty());
		passwordField.textProperty().bindBidirectional(model.passwordProperty());
		aboutMeField.textProperty().bindBidirectional(model.aboutMeProperty());
		lifeMottoField.textProperty().bindBidirectional(model.lifeMottoProperty());

		// REV: po co, skoro masz bindy?
		loginLabel.setText(model.getLogin());
		nameField.setText(model.getName());
		surnameField.setText(model.getSurname());
		emailField.setText(model.getEmail());
		passwordField.setText(model.getPassword());
		aboutMeField.setText(model.getAboutMe());
		lifeMottoField.setText(model.getLifeMotto());
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Cancel</b> button
	 * is clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 * @throws Exception 
	 */
	@FXML
	private void cancelButtonAction(ActionEvent event) throws Exception {
		LOG.debug("'Cancel' button clicked");

		((Node) event.getSource()).getScene().getWindow().hide();
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Save</b> button
	 * is clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 * @throws Exception 
	 */
	@FXML
	private void saveButtonAction(ActionEvent event) throws Exception {
		LOG.debug("'Save' button clicked");
		
		AccountVO editedProfile = new AccountVO(
				model.getProfileId(),
				model.getLogin(), 
				model.getName(),
				model.getSurname(),
				model.getEmail(),
				model.getPassword(),
				model.getAboutMe(),
				model.getLifeMotto());
		
		// REV: wywolanie powinno byc w osobnym watku
		dataProvider.updateAccount(editedProfile);

		((Node) event.getSource()).getScene().getWindow().hide();
		
		accountSearchController.searchAccounts();
	}

	public ProfileEdition getModel() {
		return model;
	}

	public AccountSearchController getAccountSearchController() {
		return accountSearchController;
	}

	public void setAccountSearchController(AccountSearchController accountSearchController) {
		this.accountSearchController = accountSearchController;
	}
}
