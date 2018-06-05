package com.test;

import com.util.asymmetry.RSA;

public class RSATest {
	public static void main(String args[]) {
		//encode
//		System.out.println("input: aaa");
		RSA rsaEn = new RSA("aaa", "aaa");
		rsaEn.generate();
		String pub1 = rsaEn.getIdRsaPub();
		String pri1 = rsaEn.getIdRsa();
		rsaEn.encode();
		String output = rsaEn.getOutput();
//		System.out.println("output: "+ output);
		
		//Decode
		RSA rsaDe = new RSA(output, "aaa");
		rsaDe.generate();
		String pub2 = rsaDe.getIdRsaPub();
		String pri2 = rsaDe.getIdRsa();
//		System.out.println(pub1);
//		System.out.println(pub2);
//		System.out.println(pub1.length());
//		System.out.println(pub2.length());
//		System.out.println("pub : " + (pub1.equals(pub2)));
//		System.out.println("pri : " + (pri1.equals(pri2)));
		rsaDe.decode();
		String input = rsaDe.getOutput();
//		System.out.println("input1: " + input);
	}
}
