package com.util;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class KeyGen {
	private Map<String, Integer> keyNumMap;
//	keyNumMap: key1 --- 0, key2 --- 1,  key3 --- 2, random --- -1

	private Map<String, Integer> algorithmNumMap;
//	algorithmNumMap: DES --- 0, AES --- 1
	
	private final String[][] keyString;
	private int keyNum;
	private int algorithmNum;
	
	public KeyGen() {
		this("Key1", "DES");
	}
	
	public KeyGen(String key, String algorithm) {
		initialize();
		keyString = new String[2][];
		keyString[0] = new String[]{"1", "2", "3"};//数组初始化的一种方法！！！
		keyString[1] = new String[]{"1", "2", "3"};//数组初始化的一种方法！！！
		keyNum = keyNumMap.get(key);
		algorithmNum = algorithmNumMap.get(algorithm);
	}
	
	private void initialize() {
		//初始化keyNumMap
		keyNumMap = new HashMap<>();
		for(int i = 0; i < 3; i++) {
			keyNumMap.put("Key" + (i + 1), i);
		}
		keyNumMap.put("Random", -1);
		
		//初始化algorithmNumMap
		algorithmNumMap = new HashMap<>();
		algorithmNumMap.put("DES", 0);
		algorithmNumMap.put("AES", 1);
	}
	
	public String getKey() {
		if(keyNum >= 0) return keyString[algorithmNum][keyNum];
		else return randomGen();
	}

	private String randomGen() {
		int keyLength;
		String key = "";
		if(algorithmNum == 0) {
			keyLength = 64;
		}else {
			keyLength = 128;
		}
		int byteLength = keyLength / 8;
		byte[] byteKey = new byte[byteLength];
		for(int i = 0; i < byteLength; i++) {
			byteKey[i] = (byte)(Math.random() * 256);///byte有正负，-2^7 ~ 2^7-1,但是不管了，二进制对就行了
			for(int j = 0; j < 8; j++) {
				key += ((byteKey[i] >> j) & 1);
			}
		}
		return key;
	}
}
