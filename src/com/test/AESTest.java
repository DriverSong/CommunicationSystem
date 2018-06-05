package com.test;

import com.util.KeyGen;
import com.util.symmetry.AES;

public class AESTest {
	public static void main(String args[]) {
		//encode
		try {
			String key = new KeyGen("Random", "AES").getKey();
			System.out.println("key: " + key);
			String input = "aaaa";
			AES aesEn = new AES(input, key);
			aesEn.encode();
			String output = aesEn.getOutput();
			System.out.println(output.length());
			System.out.println("output: " + output);
			
			//decode
			AES aesDe = new AES(output, key);
			aesDe.decode();
			String input1 = aesDe.getOutput();
			System.out.println(input1);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
