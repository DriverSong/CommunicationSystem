package com.model;

import com.sun.javafx.runtime.VersionInfo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Algorithm {
	private StringProperty symmetricAlgorithm;
	private StringProperty  symmetricKey;
	private StringProperty hashAlgorithm;
	private StringProperty inputArea;
	private StringProperty rsaInput;
	private StringProperty keyArea;
	private StringProperty idRsaArea;
	private StringProperty idRsaPubArea;
	private StringProperty hashArea;
	private StringProperty outputArea;
	private StringProperty rsaArea;
	//symmetricAlgorithm
	
	public Algorithm() {
		symmetricAlgorithm = new SimpleStringProperty("DES");
		symmetricKey = new SimpleStringProperty("Key1");
		hashAlgorithm = new SimpleStringProperty("MD5");
		inputArea = new SimpleStringProperty("");
		rsaInput = new SimpleStringProperty("");
		keyArea = new SimpleStringProperty("");
		idRsaArea = new SimpleStringProperty("");
		idRsaPubArea = new SimpleStringProperty("");
		hashArea = new SimpleStringProperty("");
		outputArea = new SimpleStringProperty("");
		rsaArea = new SimpleStringProperty("");
	}
	
	public String getSymmetricAlgorithm() {
		return symmetricAlgorithm.get();
	}
	public void setSymmetricAlgorithm(String symmetricAlgorithm) {
		this.symmetricAlgorithm.set(symmetricAlgorithm);
	}
	public StringProperty symmetricAlgorithmProperty() {
		return symmetricAlgorithm;
	}
	
	//symmetricKey
	public String getSymmetricKey() {
		return symmetricKey.get();
	}
	public void setSymmetricKey(String symmetricKey) {
		this.symmetricKey.set(symmetricKey);;
	}
	public StringProperty symmetricKeyProperty() {
		return symmetricKey;
	}
	
	//hashAlgorithm
	public String getHashAlgorithm() {
		return hashAlgorithm.get();
	}
	public void setHashAlgorithm(String hashAlgorithm) {
		this.hashAlgorithm.set(hashAlgorithm);
	}
	public StringProperty hashAlgorithmProperty() {
		return hashAlgorithm;
	}
	
	//input
	public String getInputArea() {
		return inputArea.get();
	}
	public void setInputArea(String input) {
		this.inputArea.set(input);
	}
	public StringProperty inputAreaProperty() {
		return inputArea;
	}
	
	//rsaInput1
	public StringProperty rsaInputProperty() {
		return rsaInput;
	}
	public void setRsaInput(String rsaInput1) {
		this.rsaInput.set(rsaInput1);
	}
	public String getRsaInput() {
		return rsaInput.get();
	}
	
	//keyArea
	public String getKeyArea() {
		return keyArea.get();
	}
	public void setKeyArea(String keyArea) {
		this.keyArea.set(keyArea);
	}
	public StringProperty keyAreaProperty() {
		return keyArea;
	}
	
	//idRsaArea
	public String getIdRsaArea() {
		return idRsaArea.get();
	}
	public void setIdRsaArea(String idRsaArea) {
		this.idRsaArea.set(idRsaArea);
	}
	public StringProperty idRsaAreaProperty() {
		return idRsaArea;
	}
	
	//idRsaPubArea
	public String getIdRsaPubArea() {
		return idRsaPubArea.get();
	}
	public void setIdRsaPubArea(String idRsaPubArea) {
		this.idRsaPubArea.set(idRsaPubArea);
	}
	public StringProperty idRsaPubAreaProperty() {
		return idRsaPubArea;
	}
	
	//hashArea
	public String getHashArea() {
		return hashArea.get();
	}
	public void setHashArea(String hashArea) {
		this.hashArea.set(hashArea);
	}
	public StringProperty hashAreaProperty() {
		return hashArea;
	}
	
	//outputArea
	public String getOutputArea() {
		return outputArea.get();
	}
	public void setOutputArea(String outputArea) {
		this.outputArea.set(outputArea);
	}
	public StringProperty outputAreaProperty() {
		return outputArea;
	}
	
	//rsaArea
	public void setRsaArea(String rsaArea) {
		this.rsaArea.set(rsaArea);
	}
	public String getRsaArea() {
		return rsaArea.get();
	}
	public StringProperty rsaAreaproperty() {
		return rsaArea;
	}
}
