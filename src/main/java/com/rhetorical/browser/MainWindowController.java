package com.rhetorical.browser;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

	private final String htLink = "http://";

	@FXML
	TextField addressBar;

	@FXML
	WebView webView;

	WebEngine webEngine;

	public void goToAddress(String address) {
		webEngine.load(address);

		addressBar.setText(address);
	}

	public void submitAddress() {
		String address = addressBar.getText();

		if (!address.startsWith(htLink))
			address = htLink + address;

		goToAddress(address);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		webEngine = webView.getEngine();

		EventHandler<KeyEvent> keyEventEventHandler = event -> {
			if (event.getEventType() == KeyEvent.KEY_PRESSED
				&& event.getCode() == KeyCode.ENTER) {
				submitAddress();
			}
		};

		addressBar.addEventHandler(KeyEvent.KEY_PRESSED,  keyEventEventHandler);

		//Fix stutter when first changing site
		addressBar.getText();

		webEngine.load(htLink + "start.duckduckgo.com");

		addressBar.setText(htLink + "www.duckduckgo.com");
	}
}