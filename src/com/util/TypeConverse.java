package com.util;

public class TypeConverse {
	public static String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
	
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
	
	/*
	 * 将byte数组转化为16进制String
	 */
	public static String bytes2HexString(byte[] inputBytes) {
		int length = inputBytes.length;
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < length; i++) {
			byte tempByte = inputBytes[i];
			sb.append(hex[tempByte >>> 4 & 0xf]);
			sb.append(hex[tempByte & 0xf]);
		}
		return sb.toString();
	}
	
	public static byte[] hexString2Bytes(String inputString) throws  NumberFormatException{
		int length = inputString.length();
		if (length < 1)  return null;
		byte[] outputBytes = new byte[length/2];
		for(int i = 0; i < length/2; i++) {
			int high = Integer.parseInt(inputString.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(inputString.substring(i * 2 + 1, i * 2 + 2), 16);
			outputBytes[i] = (byte) (high * 16 + low);
		}
		return outputBytes;
	}
}
