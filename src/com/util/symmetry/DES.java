package com.util.symmetry;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.model.Algorithm;
import com.util.TypeConverse;

public class DES {
	private String input;//bit数组
	private String output;//bit数组
	private String key;//由于没有bit数据类型，所以此处选用byte[]，每个元素只为0或1
	
//	public DES(String input, String key) {
//		this.input = TypeConverse.byte2Bit(input.getBytes("UTF-8"));
//		this.key = TypeConverse.string2Byte(key);
//	}

	public DES(Algorithm algorithm) {
		this.input = algorithm.getInputArea() + algorithm.getRsaArea();
		this.key = algorithm.getKeyArea();
	}
	
	public DES(String input, String key) {
		this.input = input;
		this.key = key;
	}
	
	public void setAlgorithm(Algorithm algorithm) {
		this.input = algorithm.getInputArea() + algorithm.getRsaArea();
		this.key = algorithm.getKeyArea();
	}
	
	/*
	 * TODO: encode
	 * param: algorithm.inputArea, algorithm.keyArea
	 * return: algorithm.outputArea
	 */
	public void encode() {
		try {
			byte[] inputBytes = input.getBytes();
			byte[] keyBytes = TypeConverse.hexString2Bytes(key);
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "DES");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] outputBytes = cipher.doFinal(inputBytes);
			output = TypeConverse.bytes2HexString(outputBytes);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * TODO: decode
	 * param: algorithm.inputArea, algorithm.keyArea
	 * return: algorithm.outputArea
	 */	
	public void decode() {
		try {
			byte[] inputBytes = TypeConverse.hexString2Bytes(input);
			byte[] keyBytes = TypeConverse.hexString2Bytes(key);
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "DES");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] outputBytes = cipher.doFinal(inputBytes);
			output = new String(outputBytes);
		}catch(Exception e) {
			e.printStackTrace();
		}
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
