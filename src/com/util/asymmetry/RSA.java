package com.util.asymmetry;

import java.util.Random;

import com.model.Algorithm;

public class RSA {
	private String input;
	private String idRsa;
	private String idRsaPub;
	private String output;
	
	public RSA() {
	}
	
	public RSA(Algorithm algorithm) {
		this.input = algorithm.getHashArea();
	}
	
	public void setAlgorithm (Algorithm algorithm) {
		this.input = algorithm.getHashArea();
	}
	
	public void generate() {
		this.idRsa = "idRsa" + new Random().nextInt(100);
		this.idRsaPub = "idRsaPub" + new Random().nextInt(100);
	}
	
	public void encode() {
		this.output = "encode" + this.input;
	}

	public void decode() {
		this.output = "decode" + this.output;
	}
	
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getIdRsa() {
		return idRsa;
	}

	public String getIdRsaPub() {
		return idRsaPub;
	}

	public String getOutput() {
		return output;
	}
}
