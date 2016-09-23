package com.starterkit.javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.starterkit.javafx.dataprovider.DataProvider;
import com.starterkit.javafx.dataprovider.data.AccountVO;
import com.starterkit.javafx.model.AccountSearch;
import com.starterkit.javafx.model.ProfileEdition;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AccountSearchController {

	private static final Logger LOG = Logger.getLogger(AccountSearchController.class);

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
	private TextField loginField;

	@FXML
	private TextField nameField;

	@FXML
	private TextField surnameField;

	@FXML
	private Button searchButton;

	@FXML
	private Button deleteAccountButton;

	@FXML
	private Button editProfileButton;

	@FXML
	private TableView<AccountVO> resultTable;

	@FXML
	private TableColumn<AccountVO, String> loginColumn;

	@FXML
	private TableColumn<AccountVO, String> nameColumn;

	@FXML
	private TableColumn<AccountVO, String> surnameColumn;

	private final DataProvider dataProvider = DataProvider.INSTANCE;

	private Stage profileEditionModalStage = new Stage();

	private ProfileEditionController profileEditionController;
	
	@SuppressWarnings("unused")
	private ProfileEdition profileEditionModel;

	private final AccountSearch model = new AccountSearch();

	/**
	 * The JavaFX runtime instantiates this controller.
	 * <p>
	 * The @FXML annotated fields are not yet initialized at this point.
	 * </p>
	 * 
	 * @throws IOException
	 */
	public AccountSearchController() throws IOException {
		LOG.debug("Constructor: loginField = " + loginField);
		LOG.debug("Constructor: nameField = " + nameField);
		LOG.debug("Constructor: surnameField = " + surnameField);

		FXMLLoader loader = new FXMLLoader();

		loader.setResources(ResourceBundle.getBundle("com/starterkit/javafx/bundle/base"));
		loader.setLocation(getClass().getResource("/com/starterkit/javafx/view/account-edition.fxml"));

		try {
			profileEditionModalStage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			LOG.error("IOException caught: " + e.getMessage());
		}

		profileEditionController = loader.getController();
		profileEditionController.setAccountSearchController(this);

		LOG.debug("Constructor: profileEditionController = " + profileEditionController.getClass());

		profileEditionModel = profileEditionController.getModel();

		profileEditionModalStage.initModality(Modality.APPLICATION_MODAL);
		profileEditionModalStage.setTitle("Profile Edition");
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
		LOG.debug("initialize(): loginField = " + loginField);
		LOG.debug("initialize(): nameField = " + nameField);
		LOG.debug("initialize(): surnameField = " + surnameField);

		initializeResultTable();

		loginField.textProperty().bindBidirectional(model.loginProperty());
		nameField.textProperty().bindBidirectional(model.nameProperty());
		surnameField.textProperty().bindBidirectional(model.surnameProperty());
		resultTable.itemsProperty().bind(model.resultProperty());

		searchButton.disableProperty().bind(loginField.textProperty().isEmpty()
				.and(nameField.textProperty().isEmpty())
				.and(surnameField.textProperty().isEmpty()));
		
		resetButtons();
	}

	private void initializeResultTable() {
		/*
		 * Define what properties of AccountVO will be displayed in different
		 * columns.
		 */
		loginColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLogin()));
		nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
		surnameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSurname()));
		
		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));
		
		resultTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AccountVO>() {
			
			@Override
			public void changed(ObservableValue<? extends AccountVO> observable, AccountVO oldValue, AccountVO newValue) {
				LOG.debug(newValue + " selected");
				
				if (newValue != null) {
					LOG.debug("Buttons activated");
					deleteAccountButton.setDisable(false);
					editProfileButton.setDisable(false);
				}
				
				model.setSelectedAccount(newValue);
			}
		});
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Search</b> button is
	 * clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 */
	@FXML
	private void searchButtonAction(ActionEvent event) {
		LOG.debug("'Search' button clicked");

		searchAccounts();
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Edit Profile</b> button
	 * is clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 * @throws Exception
	 */
	@FXML
	private void deleteAccountButtonAction(ActionEvent event) throws Exception {
		LOG.debug("'Delete' button clicked");

		dataProvider.deleteAccount(model.getSelectedAccount().getId());

		searchAccounts();
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Edit Profile</b> button
	 * is clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 * @throws Exception
	 */
	@FXML
	private void editProfileButtonAction(ActionEvent event) throws Exception {
		LOG.debug("'Search' button clicked");

		editSelectedAccount();
	}

	/**
	 * This implementation is correct.
	 * <p>
	 * The {@link DataProvider#findPersons(String, SexVO)} call is executed in a
	 * background thread.
	 * </p>
	 */
	public void searchAccounts() {
		Task<Collection<AccountVO>> backgroundTask = new Task<Collection<AccountVO>>() {

			@Override
			protected Collection<AccountVO> call() throws Exception {
				LOG.debug("call() called");

				Collection<AccountVO> result = dataProvider.findAccounts( //
						model.getLogin(), //
						model.getName(), //
						model.getSurname());

				return result;
			}

			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");

				Collection<AccountVO> result = getValue();

				model.setResult(new ArrayList<AccountVO>(result));

				resultTable.getSortOrder().clear();
			}
			
			@Override
			protected void failed() {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Printer error");
				alert.setContentText("Paper not loaded");
				alert.showAndWait();

			} 
		};

		new Thread(backgroundTask).start();
		
		resetButtons();
	}

	private void editSelectedAccount() {
		Task<AccountVO> backgroundTask = new Task<AccountVO>() {

			@Override
			protected AccountVO call() throws Exception {
				LOG.debug("call() called");

				AccountVO result = dataProvider.findAccount(model.getSelectedAccount().getLogin());

				return result;
			}

			/**
			 * This method will be executed in the JavaFX Application Thread
			 * when the task finishes.
			 */
			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");

				AccountVO result = getValue();

				profileEditionController.getModel().setProfileId(result.getId());
				profileEditionController.getModel().setLogin(result.getLogin());
				profileEditionController.getModel().setName(result.getName());
				profileEditionController.getModel().setSurname(result.getSurname());
				profileEditionController.getModel().setEmail(result.getEmail());
				profileEditionController.getModel().setPassword(result.getPassword());
				profileEditionController.getModel().setAboutMe(result.getAboutMe());
				profileEditionController.getModel().setLifeMotto(result.getLifeMotto());

			}
			
			@Override
			protected void failed() {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Printer error");
				alert.setContentText("Paper not loaded");
				alert.showAndWait();

			} 
		};

		new Thread(backgroundTask).start();

		profileEditionModalStage.show();
	}

	private void resetButtons() {
		LOG.debug("resetButtons() called");
		
		deleteAccountButton.setDisable(true);
		editProfileButton.setDisable(true);
	}
}
