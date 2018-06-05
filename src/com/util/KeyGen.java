package com.util;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class KeyGen {
	private static final String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
	
	private Map<String, Integer> keyNumMap;
//	keyNumMap: key1 --- 0, key2 --- 1,  key3 --- 2, random --- -1

	private Map<String, Integer> algorithmNumMap;
//	algorithmNumMap: DES --- 0, AES --- 1
	
	private final String[][] keyString = 
		{
				{
					"dc4f86d251ce2010",
					"03ec566d17ce9600",
					"d3c9da410439d579"
				},
				{
					"09f9ba7fcb22db1a13c4a7e0d93a821e",
					"3c44b62cad35a12f3b0a1d454f2868bb",
					"95226357330a7f4da5e170f7208efccc"
				}
		};
	private int keyNum;
	private int algorithmNum;
	
	public KeyGen() {
		this("Key1", "DES");
	}
	
	public KeyGen(String key, String algorithm) {
		initialize();
//		keyString = new String[2][];
//		keyString[0] = new String[]{"1", "2", "3"};//数组初始化的一种方法！！！
//		keyString[1] = new String[]{"1", "2", "3"};//数组初始化的一种方法！！！
		algorithmNum = algorithmNumMap.get(algorithm);
		keyNum = keyNumMap.get(key);
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
		StringBuffer sb = new StringBuffer();
		if(algorithmNum == 0) {
			keyLength = 64;
		}else {
			keyLength = 128;
		}
		int hexLength = keyLength / 4;
		for(int i = 0; i < hexLength; i++) {
			//Math.random() : [0, 1)
			int index = (int)(Math.random() * 16);
			sb.append(hex[index]);
		}
		return sb.toString();
	}
}
