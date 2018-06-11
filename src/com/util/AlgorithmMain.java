package com.util;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import com.model.Algorithm;
import com.util.asymmetry.RSA;
import com.util.hash.MD5;
import com.util.hash.SHA;
import com.util.symmetry.AES;
import com.util.symmetry.DES;

public class AlgorithmMain {
	private Algorithm algorithm;
	
	public AlgorithmMain(){
	}
	
	public AlgorithmMain(Algorithm algorithm) {
		this.algorithm = algorithm;
//		KeyGen keyGen = new KeyGen(algorithm.getSymmetricKey(), algorithm.getSymmetricAlgorithm());
//		this.algorithm.setKeyArea(keyGen.getKey());
	}
	
	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
//		KeyGen keyGen = new KeyGen(algorithm.getSymmetricKey(), algorithm.getSymmetricAlgorithm());
//		this.algorithm.setKeyArea(keyGen.getKey());
	}
	
	public void encode() {
		//hash
		if(algorithm.getHashAlgorithm() == "MD5") {
			MD5 md5 = new MD5(algorithm.getInputArea());
			md5.generate();
			algorithm.setHashArea(md5.getOutput());
		}else {
			SHA sha = new SHA(algorithm.getInputArea());
			sha.generate();
			algorithm.setHashArea(sha.getOutput());
		}
		
		//rsa
		// RSA(String input, String seed) 
		RSA rsa = new RSA(algorithm.getHashArea(), algorithm.getRsaInput());
		rsa.generate();
		rsa.encode();
		algorithm.setIdRsaArea(rsa.getIdRsa());
		algorithm.setIdRsaPubArea(rsa.getIdRsaPub());
		algorithm.setRsaArea(rsa.getOutput());
//		System.out.println(rsa.getOutput());
		
		//symmetricAlgorithm
		if(algorithm.getSymmetricAlgorithm() == "DES") {
			//DES(String input, String key) 
			DES des = new DES(algorithm.getInputArea() + algorithm.getRsaArea(), algorithm.getKeyArea());
			des.encode();
			algorithm.setOutputArea(des.getOutput());
		}else {
			//AES(String input, String key) 
			AES aes = new AES(algorithm.getInputArea() + algorithm.getRsaArea(), algorithm.getKeyArea());
			aes.encode();
			algorithm.setOutputArea(aes.getOutput());
		}
	}
	
	public void decode() {
		try {
			if(algorithm.getSymmetricAlgorithm() == "DES") {
				//DES(String input, String key) 
				DES des = new DES(algorithm.getInputArea(), algorithm.getKeyArea());
				des.decode();
				//得到 明文 + 1024bit(256hex)MAC码
				String output = des.getOutput();
//				System.out.println(output);
				//MAC码，长度等于RSA密钥长度，= 1024bit = 256hex
				algorithm.setRsaArea(output.substring(output.length() - 256, output.length()));
				algorithm.setOutputArea(output.substring(0, output.length() - 256));
			}else {
				//AES(String input, String key) 
				AES aes = new AES(algorithm.getInputArea(), algorithm.getKeyArea());
				aes.decode();
				//得到 明文 + 1024bit(256hex)MAC码
				String output = aes.getOutput();
//				System.out.println(output);
//				System.out.println(output);
				//MAC码，长度等于RSA密钥长度，= 1024bit = 256hex
//				System.out.println(output.length());
				algorithm.setRsaArea(output.substring(output.length() - 256, output.length()));
//				System.out.println(algorithm.getRsaArea());
				//明文
				algorithm.setOutputArea(output.substring(0, output.length() - 256));
			}
			
			//求hash
			if(algorithm.getHashAlgorithm() == "MD5") {
				MD5 md5 = new MD5(algorithm.getOutputArea());
				md5.generate();
//				System.out.println(md5.getOutput());
				algorithm.setHashArea(md5.getOutput());
			}else {
				SHA sha = new SHA(algorithm.getOutputArea());
				sha.generate();
				algorithm.setHashArea(sha.getOutput());
			}
			
			//判断hash是否一致
			RSA rsa = new RSA(algorithm.getRsaArea(), algorithm.getRsaInput());
			rsa.generate();
			rsa.decode();
			algorithm.setIdRsaArea(rsa.getIdRsa());
			algorithm.setIdRsaPubArea(rsa.getIdRsaPub());
			String rsaResult = rsa.getOutput();
//			System.out.println(rsaResult);
			if(rsaResult.equals(algorithm.getHashArea())) {
				algorithm.setOutputArea("Success Receive!\r\nPlaintext:\r\n"  + algorithm.getOutputArea());
			}else {
				algorithm.setOutputArea("Fail to receive correct plaintext!\r\nTry again!");
			}
		}catch (IllegalBlockSizeException e) {
			// TODO: handle exception
			algorithm.setOutputArea("Fail to receive correct plaintext!\r\nTry again!");
		}catch (NumberFormatException e) {
			// TODO: handle exception
			algorithm.setOutputArea("Fail to receive correct plaintext!\r\nTry again!");
		}catch (BadPaddingException e) {
			algorithm.setOutputArea("Fail to receive correct plaintext!\r\nTry again!");
		}
	}
}
