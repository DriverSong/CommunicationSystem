package com.util.symmetry;

import java.io.UnsupportedEncodingException;

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
	
	public void setAlgorithm(Algorithm algorithm) {
		this.input = algorithm.getInputArea() + algorithm.getRsaArea();
		this.key = algorithm.getKeyArea();
	}
	public void encode() {
		/*
		 * TODO: encode
		 * param: algorithm.inputArea, algorithm.keyArea
		 * return: algorithm.outputArea
		 */
		this.output = "encode" + this.input + this.key;
	}
	
	public void decode() {
		/*
		 * TODO: decode
		 * param: algorithm.inputArea, algorithm.keyArea
		 * return: algorithm.outputArea
		 */
		this.output = "decode" + this.input + this.key;
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
