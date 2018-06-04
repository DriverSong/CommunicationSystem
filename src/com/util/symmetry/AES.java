package com.util.symmetry;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.model.Algorithm;
import com.util.TypeConverse;

public class AES {
	private String input;
	private String output;
	private String key;
	
	public AES(Algorithm algorithm) {
		this.input = algorithm.getInputArea() + algorithm.getRsaArea();
		this.key = algorithm.getKeyArea();
	}
	
	public AES(String input, String key) {
		this.input = input;
		this.key = key;
	}
	
	public void setAlgorithm(Algorithm algorithm) {
		this.input = algorithm.getInputArea() + algorithm.getRsaArea();
		this.key = algorithm.getKeyArea();
	}
	
	/*
	 * @param: algorithm.inputArea, algorithm.keyArea
	 * @return: algorithm.outputArea
	 */
	public void encode() {
		try {
			byte[] inputBytes;
			inputBytes = input.getBytes("UTF-8");
			byte[] keyBytes = TypeConverse.hexString2Bytes(key);
			byte[] outputBytes = null;
			SecretKeySpec keySpec = null;
			keySpec = new SecretKeySpec(keyBytes, "AES");
			Cipher cipher;
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			outputBytes = cipher.doFinal(inputBytes);
			System.out.println(Arrays.toString(outputBytes));
			output = TypeConverse.bytes2HexString(outputBytes);
			System.out.println(output);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NoSuchAlgorithmException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (NoSuchPaddingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * param: algorithm.inputArea, algorithm.keyArea
	 * return: algorithm.outputArea
	 */
	public void decode() {
		try {
			byte[] inputBytes;
			inputBytes = TypeConverse.hexString2Bytes(input);
			byte[] keyBytes = TypeConverse.hexString2Bytes(key);
			byte[] outputBytes = null;
			SecretKeySpec keySpec = null;
			keySpec = new SecretKeySpec(keyBytes, "AES");
			Cipher cipher;
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			outputBytes = cipher.doFinal(inputBytes);
			output = new String(outputBytes);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NoSuchAlgorithmException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (NoSuchPaddingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
