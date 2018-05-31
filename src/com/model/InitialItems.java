package com.model;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InitialItems {
	private ObservableList<String> symmetricAlgorithmItems;
	private ObservableList <String> symmetricKey;
	private ObservableList<String> hashAlgorithmItems;
	
	public InitialItems() {
		this.symmetricAlgorithmItems = FXCollections.observableArrayList("AES", "DES");
		this.symmetricKey = FXCollections.observableArrayList("Key1", "Key2", "Key3", "Random");
		this.hashAlgorithmItems = FXCollections.observableArrayList("MD5", "SHA");
	}

	public ObservableList<String> getSymmetricAlgorithmItems() {
		return symmetricAlgorithmItems;
	}

	public void setSymmetricAlgorithmItems(ObservableList<String> symmetricAlgorithmItems) {
		this.symmetricAlgorithmItems = symmetricAlgorithmItems;
	}

	public ObservableList<String> getSymmetricKey() {
		return symmetricKey;
	}

	public void setSymmetricKey(ObservableList<String> symmetricKey) {
		this.symmetricKey = symmetricKey;
	}

	public ObservableList<String> getHashAlgorithmItems() {
		return hashAlgorithmItems;
	}

	public void setHashAlgorithmItems(ObservableList<String> hashAlgorithmItems) {
		this.hashAlgorithmItems = hashAlgorithmItems;
	}
}
