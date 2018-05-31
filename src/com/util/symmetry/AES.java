package com.util.symmetry;

import com.model.Algorithm;

public class AES {
	private String input;
	private String output;
	private String key;
	
	public AES(Algorithm algorithm) {
		this.input = algorithm.getInputArea() + algorithm.getRsaArea();
		this.key = algorithm.getKeyArea();
	}
	
	public void setAlgorithm(Algorithm algorithm) {
		this.input = algorithm.getInputArea() + algorithm.getRsaArea();
		this.key = algorithm.getKeyArea();
	}
	public void encode() {
		/*
		 * TODO: encode
		 * param: algorithm.inputArea, algorithm.keyArea
		 * return: algorithm.outputArea
		 */
		this.output = "encode" + this.input + this.key;
	}
	
	public void decode() {
		/*
		 * TODO: decode
		 * param: algorithm.inputArea, algorithm.keyArea
		 * return: algorithm.outputArea
		 */
		this.output = "decode" + this.input + this.key;
	}
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOutput() {
		return output;
	}
}
