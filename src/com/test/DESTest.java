package com.test;

import com.util.KeyGen;
import com.util.symmetry.DES;

public class DESTest {
	public static void main(String args[]) {
		String key = new KeyGen("Random", "DES").getKey();
		String input0 = "aaa";
		System.out.println(input0);
		//encode
		DES desEn = new DES(input0, key);
		desEn.encode();
		String output = desEn.getOutput();
		System.out.println(output);
		//decode
		DES desDe = new DES(output, key);
		desDe.decode();
		String input = desDe.getOutput();
		System.out.println(input);
	}
}
