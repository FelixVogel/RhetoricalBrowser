package com.rhetorical.browser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		URL url = Paths.get("src/main/java/com/rhetorical/browser/fxml/MainScene.fxml").toUri().toURL();
		Parent root = FXMLLoader.load(url);
		stage.setTitle("Rhetorical's Browser");
		stage.setScene(new Scene(root, 1250, 690));
		stage.setFullScreen(false);
		stage.getIcons().add(new Image("com/rhetorical/browser/image/icon.jpg"));
		stage.show();
	}
}