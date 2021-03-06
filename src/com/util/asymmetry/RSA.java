package com.util.asymmetry;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import com.model.Algorithm;
import com.util.TypeConverse;

public class RSA {
	private String seed;
	private String input;
	private String idRsa;
	private String idRsaPub;
	private String output;
	private Map<String, Key> rsaMap = new HashMap<>();
	private static int KEYSIZE = 1024;
	
	public RSA() {
	}
	
	public RSA(String input, String seed) {
		this.input = input;
		this.seed = seed;
	}
	
	public void setAlgorithm (Algorithm algorithm) {
		this.seed = algorithm.getRsaInput();
		this.input = algorithm.getHashArea();
	}
	
	public void generate() {
		try {
			byte[] seedBytes = seed.getBytes();
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			//需要在getInstance后，再setSeed，才能根据seed生成伪随机数
			sr.setSeed(seedBytes);
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(KEYSIZE, sr);
			KeyPair kp = kpg.generateKeyPair();
			//pub
			Key pub = kp.getPublic();
			rsaMap.put("pub", pub);
			byte[] pubBytes = pub.getEncoded();
			idRsaPub = TypeConverse.bytes2HexString(pubBytes);
			//pri
			Key pri = kp.getPrivate();
			rsaMap.put("pri", pri);
			byte[] priBytes = pri.getEncoded();
			idRsa = TypeConverse.bytes2HexString(priBytes);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//签名
	public void encode() {
		try {
			//PKCS8EncodedKeySpec
			KeySpec kp = new PKCS8EncodedKeySpec(TypeConverse.hexString2Bytes(idRsa));
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PrivateKey pri = kf.generatePrivate(kp);
//			Key pub = rsaMap.get("pub");
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pri);
			byte[] inputBytes = input.getBytes();
//			System.out.println(Arrays.toString(inputBytes));
			byte[] outputBytes = cipher.doFinal(inputBytes);
//			System.out.println(Arrays.toString(outputBytes));
			output = TypeConverse.bytes2HexString(outputBytes);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//验证身份
	public void decode() throws NumberFormatException, IllegalBlockSizeException, BadPaddingException{
		try {
			//X509EncodedKeySpec
			KeySpec kp = new X509EncodedKeySpec(TypeConverse.hexString2Bytes(idRsaPub));
			KeyFactory kf;
			kf = KeyFactory.getInstance("RSA"); 
			PublicKey pub;
			pub = kf.generatePublic(kp);
//			Key pri = rsaMap.get("pri");
			Cipher cipher;
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, pub);
			byte[] inputBytes = TypeConverse.hexString2Bytes(input);
//			System.out.println(Arrays.toString(inputBytes));
			byte[] outputBytes;
			outputBytes = cipher.doFinal(inputBytes);
			output = new String(outputBytes);
		}catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (InvalidKeySpecException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}catch (NoSuchPaddingException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}catch (InvalidKeyException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
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
