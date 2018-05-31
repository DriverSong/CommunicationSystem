package com.util;

public class TypeConverse {
	//将输入的String转化为Byte[]，用于将字符串的密钥转化为Byte[]数组，此时每个byte[]元素保存的仅仅是0或1(即1bit数据)
	public static byte[] string2Byte(String inString) {
		int length;
		byte[] outBytes;
		String[] inStrings = inString.split("");
		length = inStrings.length;
		outBytes = new byte[length];
		for(int i = 0; i < length; i++) {
			outBytes[i] = Byte.parseByte(inStrings[i]);//xxx.parsexxx,将string转为int，byte等的统一方法
		}
		return outBytes;
	}
	
	//将byte数组转化为bit数组，但由于java没有bit基本数据类型，所以bit数组也用byte[]来保存
	public static byte[] byte2Bit(byte[] inBytes) {
		int length;
		byte[] outBits;
		length = inBytes.length;
		outBits = new byte[length * 8];
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < 8; j++) {
				/*循环右移取bit，若原本的byte为43（00100111），
				 * 则最后得到的bit数组是从低位开始存储的：
				 * 1,1,1,0,0,1,0,0
				 */
				outBits[i * 8 + j] = (byte)((inBytes[i] >> j) & 1); 
			}
		}
		return outBits;
	}
	
	//将bit数组转化为byte数组
	public static byte[] bit2Byte(byte[] inBits) {
		int length;
		byte[] outBytes;
		length = inBits.length;
		outBytes = new byte[length / 8];
		for(int i = 0; i < length / 8; i++) {
			outBytes[i] = 0;
			for(int j = 0; j < 8; j++) {
				outBytes[i] += inBits[i * 8 + j] << j;
			}
		}
		return outBytes;
	}
	
	//将byte转为long
	public static long byte2Long(byte inByte) {
		return inByte < 0 ? inByte & 0x7F + 128 : inByte;
	}
}
