package com.view;

import com.MainApp;
import com.model.Algorithm;
import com.model.InitialItems;
import com.util.AlgorithmMain;
import com.util.KeyGen;

import javafx.beans.DefaultProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PrimaryStageController {
	@FXML
	private ChoiceBox<String> symmetricAlgorithmChoices;
	@FXML
	private ChoiceBox<String> symmetricKeyChoices;
	@FXML
	private ChoiceBox<String> HashAlgorithmChoices;
	@FXML
	private TextArea keyArea;
	@FXML 
	private TextArea idRsaArea;
	@FXML
	private TextArea idRsaPubArea;
	@FXML
	private TextArea inputArea;
	@FXML
	private TextArea hashArea;
	@FXML
	private TextArea outputArea;
	@FXML
	private PasswordField rsaInput1;
	@FXML
	private PasswordField rsaInput2;
	
	private MainApp main;
	
	private Algorithm algorithm;
	
	public PrimaryStageController() {
		this.algorithm = new Algorithm();
	}
	
	@FXML 
	private void initialize() {
		InitialItems initialItems = new InitialItems();
		symmetricAlgorithmChoices.setItems(initialItems.getSymmetricAlgorithmItems());
		symmetricAlgorithmChoices.getSelectionModel().select(0);
		symmetricKeyChoices.setItems(initialItems.getSymmetricKey());
		symmetricKeyChoices.getSelectionModel().select(0);
		HashAlgorithmChoices.setItems(initialItems.getHashAlgorithmItems());
		HashAlgorithmChoices.getSelectionModel().select(0);
		
		showKeyArea(symmetricAlgorithmChoices.getSelectionModel().getSelectedItem(), 
				symmetricKeyChoices.getSelectionModel().getSelectedItem());
		
		keyArea.setEditable(false);
		idRsaArea.setEditable(false);
		idRsaPubArea.setEditable(false);
		hashArea.setEditable(false);
		outputArea.setEditable(false);
		
		symmetricAlgorithmChoices.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showKeyArea(newValue, symmetricKeyChoices.getSelectionModel().getSelectedItem()));
		symmetricKeyChoices.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldvalue, newValue) -> showKeyArea(symmetricAlgorithmChoices.getSelectionModel().getSelectedItem(), newValue));;
		
	}
	
	private void showKeyArea(String symmetricAlgorithm, String symmetricKey) {
//		if(symmetricKey != "Random") {
//			KeyGen kg = new KeyGen(symmetricKey, symmetricAlgorithm);
//			keyArea.setText(kg.getKey());
//		}else {
//			keyArea.setText("Random");
//		}
		KeyGen kg = new KeyGen(symmetricKey, symmetricAlgorithm);
		keyArea.setText(kg.getKey());
	}

	@FXML
	private void handleEncode() {
		//选择hash算法
		algorithm.setHashAlgorithm(HashAlgorithmChoices.getSelectionModel().getSelectedItem());
		//选择对称加密算法
		algorithm.setSymmetricAlgorithm(symmetricAlgorithmChoices.getSelectionModel().getSelectedItem());
		//选择密钥序号
		algorithm.setSymmetricKey(symmetricKeyChoices.getSelectionModel().getSelectedItem());
		//手动输入
		algorithm.setInputArea(inputArea.getText());
		//输入rsa Seed
		algorithm.setRsaInput(rsaInput1.getText());
		//输入密钥
		algorithm.setKeyArea(keyArea.getText());
		
		AlgorithmMain algorithmMain = new AlgorithmMain(algorithm);
		algorithmMain.encode();
		
//		System.out.println(algorithm.getKeyArea());
//		keyArea.setText(algorithm.getKeyArea());
		idRsaArea.setText(algorithm.getIdRsaArea());
		idRsaPubArea.setText(algorithm.getIdRsaPubArea());
		hashArea.setText(algorithm.getHashArea());
		outputArea.setText(algorithm.getOutputArea());
	}
	
	@FXML
	private void handleDecode() {
		//选择hash算法
		algorithm.setHashAlgorithm(HashAlgorithmChoices.getSelectionModel().getSelectedItem());
		//选择对称加密算法
		algorithm.setSymmetricAlgorithm(symmetricAlgorithmChoices.getSelectionModel().getSelectedItem());
		//选择密钥序号
		algorithm.setSymmetricKey(symmetricKeyChoices.getSelectionModel().getSelectedItem());
		//手动输入
		algorithm.setInputArea(inputArea.getText());
		//输入rsa Seed
		algorithm.setRsaInput(rsaInput1.getText());
		//输入密钥
		algorithm.setKeyArea(keyArea.getText());
	
		AlgorithmMain algorithmMain = new AlgorithmMain(algorithm);
		algorithmMain.decode();
		
		idRsaArea.setText(algorithm.getIdRsaArea());
		idRsaPubArea.setText(algorithm.getIdRsaPubArea());
		hashArea.setText(algorithm.getHashArea());
		outputArea.setText(algorithm.getOutputArea());
	}

	public void setMain(MainApp mainApp) {
		this.main = main;
//		this.algorithm = main.getAlgorithm();
	}
	
	
}
