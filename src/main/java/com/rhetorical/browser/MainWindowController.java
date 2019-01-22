package com.rhetorical.browser;

import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.event.ChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

	private String homeAddress = "";
	private String prevAddress = "";
	private String nextAddress = "";

	@FXML
	public TextField addressBar;

	@FXML
	public Button backButton;

	@FXML
	public Button forwardButton;

	@FXML
	public Button homeButton;

	@FXML
	public Button reloadButton;

	@FXML
	public WebView webView;

	@FXML
	public HBox hBox;


	private WebEngine webEngine;

	private void updatePreviousAddress(String currentAddress) {
		prevAddress = currentAddress;
		if (backButton.isDisable()) {
			backButton.setDisable(false);
		}
	}

	private void updateNextAddress(String currentAddress) {
		nextAddress = currentAddress;
		if (( nextAddress.equals(webEngine.getLocation()) || nextAddress.equals("") || nextAddress.equals(prevAddress) ) && !forwardButton.isDisable()) {
			forwardButton.setDisable(true);
		} else {
			forwardButton.setDisable(false);
		}
	}

	public void goBack() {
		String loc = webEngine.getLocation();
		goToAddress(prevAddress);
		updateNextAddress(loc);
	}

	public void goForward() {
		goToAddress(nextAddress);
		updateNextAddress("");
	}

	public void reload() {
		webEngine.reload();
	}

	public void goHome() {
		webEngine.load(homeAddress);
	}

	public void goToAddress(String input) {

		updatePreviousAddress(webEngine.getLocation());
		updateNextAddress("");

		webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == Worker.State.FAILED) {
				goToAddress("https://duckduckgo.com/?q=" + input);
			}
		});

		String address = input;

		if (!address.toLowerCase().startsWith("http") && !address.toLowerCase().startsWith("www")) {
			if (address.contains(".")) {
				address = "http://" + address;
			} else {
				goToAddress("https://duckduckgo.com/?q=" + input);
			}
		}

		webEngine.load(address);
	}

	public void openFile(String address, String viewedAddress) {
		webEngine.loadContent(address);
	}

	public void submitAddress() {
		String address = addressBar.getText();

		if (address.equalsIgnoreCase("browser::settings")) {
			openFile("com/rhetorical/browser/web/settings/settings.html", "browser::settings");
		}

		goToAddress(address);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		webEngine = webView.getEngine();

		HBox.setHgrow(addressBar, Priority.ALWAYS);

		addressBar.addEventHandler(KeyEvent.KEY_PRESSED,  (event) -> {
			if (event.getEventType() == KeyEvent.KEY_PRESSED
					&& event.getCode() == KeyCode.ENTER) {
				submitAddress();
			}
		});

		//Fix stutter when first changing site
		addressBar.getText();

		String htLink = "http://";
		homeAddress = htLink + "start.duckDuckGo.com";
		goHome();

		webEngine.locationProperty().addListener((observable) -> {
			updatePreviousAddress(webEngine.getLocation());
			if (prevAddress.equals(nextAddress)) {
				updateNextAddress("");
			}
			addressBar.setText(prevAddress);
		});
	}
}