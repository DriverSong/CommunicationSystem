package com.util.hash;

import java.io.UnsupportedEncodingException;

import com.model.Algorithm;
import com.util.TypeConverse;

public class MD5 {
	private String input;
	private String output;
	private static final String hexs[] = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
	private static final long A = 0x67452301L;/*即0x01234567L，这样存储是由于inputString是存在
																								byte数组里面的，而一个byte数组为8bit（两个16进制），
																								存储的时候低位在前，高位在后，所以为了计算一致，
																								存为0x67452301L*/
	private static final long B = 0xefcdab89L;//即0x89abcdefL
	private static final long C = 0x98badcfeL;//即0xfedcba98L
	private static final long D = 0x10325476L;//即0x76543210L
	private long[] magicNum;
	
	//下面这些S11-S44实际上是一个4*4的矩阵，在四轮循环运算中用到
    static final int S11 = 7;
    static final int S12 = 12;
    static final int S13 = 17;
    static final int S14 = 22;

    static final int S21 = 5;
    static final int S22 = 9;
    static final int S23 = 14;
    static final int S24 = 20;

    static final int S31 = 4;
    static final int S32 = 11;
    static final int S33 = 16;
    static final int S34 = 23;

    static final int S41 = 6;
    static final int S42 = 10;
    static final int S43 = 15;
    static final int S44 = 21;
	
	public MD5(Algorithm algorithm) {
		magicNum = new long[]{A, B, C, D};
		this.input = algorithm.getInputArea();
	}
	
	public MD5() {
	}
	
	public void setAlgorithm (Algorithm algorithm) {
		this.input = algorithm.getInputArea();
	}
	
	public void generate() {
		//将输入数据按每组512bit（64byte）划分，每组又分为16个小组（每小组32bit（4byte））
		int length;//数组byte数组总长
		int residueLength;//按每组64byte分组后，多余的byte数
		byte[] inputBytes = null;//输入String转化成的byte数组
		byte[] residueBytes = new byte[64];//用于存放多余的输入byte数组
		long[] groupLongs = null;//存放每小组的数据，每组4byte（刚好和long数据类型长度一直，存放在long数组中）
		int groupNum;
		try {
			inputBytes = input.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		length = inputBytes.length;
		groupNum = length / 64;
		for(int i = 0; i < groupNum; i++) {
			groupLongs = devideGroup(inputBytes, i * 64);//每大组包含64byte，第i组是从第i*64个byte开始的
			magicNumUpdate(groupLongs);
		}
		
		//将剩下的input进行分组
		residueLength = length%64;
		//若剩余长度不小于56byte（即448bit），则只需再分一组
		if(residueLength <= 56) {
			for(int i = 0; i < residueLength; i++) {
				residueBytes[i] = inputBytes[length - residueLength + i];
			}
			if(residueLength < 56) {
				residueBytes[residueLength] = (byte) (1 << 7);//左移7为，成为1000,0000
				for(int i = residueLength + 1; i < 56; i++) {
					residueBytes[i] = 0;
				}
			}
			//最后8byte存储长度
			long len = (long)(length) << 3;//<<3 即乘8，存储长度按bit记
			for(int i =0; i < 8; i++) {
				residueBytes[56 + i] = (byte)(len & 0xFFL);
				len = len >> 8;
			}
			groupLongs = devideGroup(residueBytes, 0);
			magicNumUpdate(groupLongs);
		}else {
			//第一组
			for(int i = 0;i < residueLength; i++) {
				residueBytes[i] = inputBytes[length * 64 + i];
			}
			residueBytes[residueLength] = (byte) (1 << 7);//左移7为，成为1000,0000
			for(int i = residueLength + 1; i < 64; i++) {
				residueBytes[i] = 0;
			}
			groupLongs = devideGroup(residueBytes, 0);
			magicNumUpdate(groupLongs);
			
			//第二组
			for(int i = 0; i < 56; i++) {
				residueBytes[i] = 0;
			}
			//最后8byte存储长度
			long len = (long)(length) << 3;//<<3 即乘8，存储长度按bit记
			for(int i =0; i < 8; i++) {
				residueBytes[56 + i] = (byte)(len & 0xFFL);
				len = len >> 8;
			}
			groupLongs = devideGroup(residueBytes, 0);
			magicNumUpdate(groupLongs);
		}
		
		/*
		 * magicNum更新结束，将结果组合为128bit的字符串（output）
		 * 需注意，存储的时候A，B，C，D是按byte由低位到高位进行存储的，需要进行转换
		 */
		output = "";
		for(int i = 0; i < 4; i++) {
			long tempLong = magicNum[i];
			for(int j = 0; j < 4; j++) {
//				这中方法有错，因为若有一组tempLong & 0xFFL < 0xFL时，得到的string只有一位。
//				output += Integer.toHexString((int)(tempLong & 0xFFL));
//				tempLong = tempLong >> 8;
				String hex1 = hexs[(int)(tempLong & 0x0FL)];
				tempLong = tempLong >> 4;
				String hex2 = hexs[(int)(tempLong & 0x0FL)];
				tempLong = tempLong >> 4;
				output = output + hex2 + hex1;
			}
		}
	}

	/*
	 * 每组进行四轮迭代，更新magicNum（A，B，C，D）的值
	 * @param long[]
	 * @return
	 */
	private void magicNumUpdate(long[] groups) {
		long a = magicNum[0];
		long b = magicNum[1];
		long c = magicNum[2];
		long d = magicNum[3];
		
		/*第一轮*/
        a = FF(a, b, c, d, groups[0], S11, 0xd76aa478L); /* 1 */
        d = FF(d, a, b, c, groups[1], S12, 0xe8c7b756L); /* 2 */
        c = FF(c, d, a, b, groups[2], S13, 0x242070dbL); /* 3 */
        b = FF(b, c, d, a, groups[3], S14, 0xc1bdceeeL); /* 4 */
        a = FF(a, b, c, d, groups[4], S11, 0xf57c0fafL); /* 5 */
        d = FF(d, a, b, c, groups[5], S12, 0x4787c62aL); /* 6 */
        c = FF(c, d, a, b, groups[6], S13, 0xa8304613L); /* 7 */
        b = FF(b, c, d, a, groups[7], S14, 0xfd469501L); /* 8 */
        a = FF(a, b, c, d, groups[8], S11, 0x698098d8L); /* 9 */
        d = FF(d, a, b, c, groups[9], S12, 0x8b44f7afL); /* 10 */
        c = FF(c, d, a, b, groups[10], S13, 0xffff5bb1L); /* 11 */
        b = FF(b, c, d, a, groups[11], S14, 0x895cd7beL); /* 12 */
        a = FF(a, b, c, d, groups[12], S11, 0x6b901122L); /* 13 */
        d = FF(d, a, b, c, groups[13], S12, 0xfd987193L); /* 14 */
        c = FF(c, d, a, b, groups[14], S13, 0xa679438eL); /* 15 */
        b = FF(b, c, d, a, groups[15], S14, 0x49b40821L); /* 16 */

        /*第二轮*/
        a = GG(a, b, c, d, groups[1], S21, 0xf61e2562L); /* 17 */
        d = GG(d, a, b, c, groups[6], S22, 0xc040b340L); /* 18 */
        c = GG(c, d, a, b, groups[11], S23, 0x265e5a51L); /* 19 */
        b = GG(b, c, d, a, groups[0], S24, 0xe9b6c7aaL); /* 20 */
        a = GG(a, b, c, d, groups[5], S21, 0xd62f105dL); /* 21 */
        d = GG(d, a, b, c, groups[10], S22, 0x2441453L); /* 22 */
        c = GG(c, d, a, b, groups[15], S23, 0xd8a1e681L); /* 23 */
        b = GG(b, c, d, a, groups[4], S24, 0xe7d3fbc8L); /* 24 */
        a = GG(a, b, c, d, groups[9], S21, 0x21e1cde6L); /* 25 */
        d = GG(d, a, b, c, groups[14], S22, 0xc33707d6L); /* 26 */
        c = GG(c, d, a, b, groups[3], S23, 0xf4d50d87L); /* 27 */
        b = GG(b, c, d, a, groups[8], S24, 0x455a14edL); /* 28 */
        a = GG(a, b, c, d, groups[13], S21, 0xa9e3e905L); /* 29 */
        d = GG(d, a, b, c, groups[2], S22, 0xfcefa3f8L); /* 30 */
        c = GG(c, d, a, b, groups[7], S23, 0x676f02d9L); /* 31 */
        b = GG(b, c, d, a, groups[12], S24, 0x8d2a4c8aL); /* 32 */

        /*第三轮*/
        a = HH(a, b, c, d, groups[5], S31, 0xfffa3942L); /* 33 */
        d = HH(d, a, b, c, groups[8], S32, 0x8771f681L); /* 34 */
        c = HH(c, d, a, b, groups[11], S33, 0x6d9d6122L); /* 35 */
        b = HH(b, c, d, a, groups[14], S34, 0xfde5380cL); /* 36 */
        a = HH(a, b, c, d, groups[1], S31, 0xa4beea44L); /* 37 */
        d = HH(d, a, b, c, groups[4], S32, 0x4bdecfa9L); /* 38 */
        c = HH(c, d, a, b, groups[7], S33, 0xf6bb4b60L); /* 39 */
        b = HH(b, c, d, a, groups[10], S34, 0xbebfbc70L); /* 40 */
        a = HH(a, b, c, d, groups[13], S31, 0x289b7ec6L); /* 41 */
        d = HH(d, a, b, c, groups[0], S32, 0xeaa127faL); /* 42 */
        c = HH(c, d, a, b, groups[3], S33, 0xd4ef3085L); /* 43 */
        b = HH(b, c, d, a, groups[6], S34, 0x4881d05L); /* 44 */
        a = HH(a, b, c, d, groups[9], S31, 0xd9d4d039L); /* 45 */
        d = HH(d, a, b, c, groups[12], S32, 0xe6db99e5L); /* 46 */
        c = HH(c, d, a, b, groups[15], S33, 0x1fa27cf8L); /* 47 */
        b = HH(b, c, d, a, groups[2], S34, 0xc4ac5665L); /* 48 */

        /*第四轮*/
        a = II(a, b, c, d, groups[0], S41, 0xf4292244L); /* 49 */
        d = II(d, a, b, c, groups[7], S42, 0x432aff97L); /* 50 */
        c = II(c, d, a, b, groups[14], S43, 0xab9423a7L); /* 51 */
        b = II(b, c, d, a, groups[5], S44, 0xfc93a039L); /* 52 */
        a = II(a, b, c, d, groups[12], S41, 0x655b59c3L); /* 53 */
        d = II(d, a, b, c, groups[3], S42, 0x8f0ccc92L); /* 54 */
        c = II(c, d, a, b, groups[10], S43, 0xffeff47dL); /* 55 */
        b = II(b, c, d, a, groups[1], S44, 0x85845dd1L); /* 56 */
        a = II(a, b, c, d, groups[8], S41, 0x6fa87e4fL); /* 57 */
        d = II(d, a, b, c, groups[15], S42, 0xfe2ce6e0L); /* 58 */
        c = II(c, d, a, b, groups[6], S43, 0xa3014314L); /* 59 */
        b = II(b, c, d, a, groups[13], S44, 0x4e0811a1L); /* 60 */
        a = II(a, b, c, d, groups[4], S41, 0xf7537e82L); /* 61 */
        d = II(d, a, b, c, groups[11], S42, 0xbd3af235L); /* 62 */
        c = II(c, d, a, b, groups[2], S43, 0x2ad7d2bbL); /* 63 */
        b = II(b, c, d, a, groups[9], S44, 0xeb86d391L); /* 64 */
		
		magicNum[0] += a;
		magicNum[1] += b;
		magicNum[2] += c;
		magicNum[3] += d;
		for(int i = 0; i < 4; i++) {
			magicNum[i] &= 0xFFFFFFFFL;
		}
	}

	/*
	 * FF(a,b,c,d,Mj,s,ti)表示a=b+((a+F(b,c,d)+Mj+ti)<<<s)
	 * @param long, long, long, long, long, int, long
	 * @return long
	 */
	private long FF(long a, long b, long c, long d, long M, int s, long t) {
		a += (F(b, c, d) & 0xFFFFFFFFL) + M + t;//& 以及 && 的优先级均低于+-×/，所以此处括号不能省略
		a = ((a & 0xFFFFFFFFL) << s) | ((a & 0xFFFFFFFFL) >>> (32 - s));
		a += b;
		return (a & 0xFFFFFFFFL);
	}
	
	private long F(long x, long y, long z) {
		return (x & y) | ((~x) & z);
	}

	/*
	 * GG(a,b,c,d,Mj,s,ti)表示a=b+((a+G(b,c,d)+Mj+ti)<<<s)
	 * @param long, long, long, long, long, int, long
	 * @return long
	 */
	private long GG(long a, long b, long c, long d, long M, int s, long t) {
		a += (G(b, c, d) & 0xFFFFFFFFL) + M + t;
		a = ((a & 0xFFFFFFFFL) << s) | ((a & 0xFFFFFFFFL) >>> (32 - s));
		a += b;
		return (a & 0xFFFFFFFFL);
	}
	
	private long G(long x, long y, long z) {
		return (x & z) | (y & (~z));
	}

	/*
	 * HH(a,b,c,d,Mj,s,ti)表示a=b+((a+H(b,c,d)+Mj+ti)<<<s)
	 * @param long, long, long, long, long, int, long
	 * @return long
	 */
	private long HH(long a, long b, long c, long d, long M, int s, long t) {
		a += (H(b, c, d) & 0xFFFFFFFFL) + M + t;
		a = ((a & 0xFFFFFFFFL) << s) | ((a & 0xFFFFFFFFL) >>> (32 - s));
		a += b;
		return (a & 0xFFFFFFFFL);
	}
	
	private long H(long x, long y, long z) {
		return x ^ y ^ z;
	}

	/*
	 * II(a,b,c,d,Mj,s,ti)表示a=b+((a+I(b,c,d)+Mj+ti)<<<s)
	 * @param long, long, long, long, long, int, long
	 * @return long
	 */
	private long II(long a, long b, long c, long d, long M, int s, long t) {
		a += (I(b, c, d) & 0xFFFFFFFFL) + M + t;
		a = ((a & 0xFFFFFFFFL) << s) | ((a & 0xFFFFFFFFL) >>> (32 - s));
		a += b;
		return (a & 0xFFFFFFFFL);
	}
	
	
	private long I(long x, long y, long z) {
		return y ^ (x | (~z));
	}

	//将输入数据分为64byte一组
	private long[] devideGroup(byte[] inputBytes, int prefix) {
		int groupLength = 16;//每组分为16小组，一个小组4byte
		long[] groupLongs = new long[groupLength];
		for(int i = 0; i < groupLength; i++) {
			/*
			 * 4byte组合为long时需要先将byte转long
			 * byte是带符号位的，byte转long时需对符号位进行操作
			 */
			groupLongs[i] = TypeConverse.byte2Long(inputBytes[prefix + i * 4 + 0]) | 
					(TypeConverse.byte2Long(inputBytes[prefix + i * 4 + 1]) << 8) |
					(TypeConverse.byte2Long(inputBytes[prefix + i * 4 + 2]) << 16) | 
					(TypeConverse.byte2Long(inputBytes[prefix + i * 4 + 3]) << 24) ;
		}
		return groupLongs;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}
}
