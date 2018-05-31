package com;

import java.io.IOException;

import com.model.Algorithm;
import com.view.PrimaryStageController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
//	private Algorithm algorithm;
	private Stage primaryStage;
	private BorderPane rootLayout;
//	private Algorithm algorithm = new Algorithm();

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Cyber Security System");
		
		initRootLayout();
		
		showOverview();
		
	}

	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane)loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch (IOException e) {
			e.printStackTrace();;
		}
	}
	
	private void showOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PrimaryStage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			rootLayout.setCenter(page);
			
//			PrimaryStageController primaryStageController = new PrimaryStageController();
			PrimaryStageController primaryStageController = loader.getController();
			primaryStageController.setMain(this);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
//	public Algorithm getAlgorithm() {
//		return algorithm;
//	}

	public static void main(String[] args) {
		launch(args);
	}
}
