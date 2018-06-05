package com.util.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.model.Algorithm;
import com.util.TypeConverse;

/*
 * SHA-1： 任意bit转化为160bit
 */
public class SHA {
	private String input;
	private String output;
	private static final String HashAlgorithm = "SHA-1";
	
	
	public SHA(String input) {
		this.input = input;
	}
	
	public void setAlgorithm(Algorithm algorithm) {
		this.input = algorithm.getInputArea();
	}
	
	public void generate() {
		try {
			byte[] inputBytes = input.getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance(HashAlgorithm);
			sha.update(inputBytes);
			byte[] digests = sha.digest();
			output = TypeConverse.bytes2HexString(digests);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
