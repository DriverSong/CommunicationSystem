package com.util;

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
		KeyGen keyGen = new KeyGen(algorithm.getSymmetricKey(), algorithm.getSymmetricAlgorithm());
		this.algorithm.setKeyArea(keyGen.getKey());
	}
	
	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
		KeyGen keyGen = new KeyGen(algorithm.getSymmetricKey(), algorithm.getSymmetricAlgorithm());
		this.algorithm.setKeyArea(keyGen.getKey());
	}
	
	public void encode() {
		if(algorithm.getHashAlgorithm() == "MD5") {
			MD5 md5 = new MD5(algorithm);
			md5.generate();
			algorithm.setHashArea(md5.getOutput());
		}else {
			SHA sha = new SHA(algorithm);
			sha.generate();
			algorithm.setHashArea(sha.getOutput());
		}
		
		RSA rsa = new RSA(algorithm);
		rsa.generate();
		rsa.encode();
		algorithm.setIdRsaArea(rsa.getIdRsa());
		algorithm.setIdRsaPubArea(rsa.getIdRsaPub());
		algorithm.setRsaArea(rsa.getOutput());
		
		if(algorithm.getSymmetricAlgorithm() == "DES") {
			DES des = new DES(algorithm);
			des.encode();
			algorithm.setOutputArea(des.getOutput());
		}else {
			AES aes = new AES(algorithm);
			aes.encode();
			algorithm.setOutputArea(aes.getOutput());
		}
		
	}
}
