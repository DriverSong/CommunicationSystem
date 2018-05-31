package com.util.hash;

import com.model.Algorithm;

public class SHA {
	private String input;
	private String output;
	
	public SHA() {
	}
	
	public SHA(Algorithm algorithm) {
		this.input = algorithm.getInputArea();
	}
	
	public void setAlgorithm(Algorithm algorithm) {
		this.input = algorithm.getInputArea();
	}
	
	public void generate() {
		this.output = "SHA" + this.input;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}
}
