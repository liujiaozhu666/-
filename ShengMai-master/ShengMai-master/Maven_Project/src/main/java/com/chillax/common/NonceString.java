package com.chillax.common;


import java.net.InetAddress;
import java.security.SecureRandom;

public class NonceString {
	private static int nextSeq32767 = 0;
	private static String netAddrHexStr = null;
	private static SecureRandom seederStatic = null;
	private static String portHexStr = "0000";
	private static byte[] addrBytes = null;

	// 16进制是0~F，32进制是0~V，36进制是0~Z
	private static char[] radixDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	static {
		try {
			seederStatic = new SecureRandom();
			seederStatic.nextInt();
			InetAddress.getLocalHost().getHostAddress();
			addrBytes = InetAddress.getLocalHost().getAddress();
			netAddrHexStr = toHex(toInt(addrBytes), 8);
		} catch (Exception ex) {
			throw new RuntimeException("get netAddr error" + ex.getMessage());
		}
	}

	public static String generate() {
		StringBuffer uid = new StringBuffer(32);

		// 得到系统时间的32进制字符串, 总共是9个字符
		uid.append(getSystemMillisRadix32());

		// 拼上32进制的三个字符的循环数，取值0~32767，并发的时候，即使是在同一个毫秒数，也能得到不同的ID
		// 在同一个毫秒数上，总共有32767个不同数, 按目前的机器速度不会产生重复
		uid.append(getSeqRadix32());

		// get the Internet address，8个十六进制字符
		uid.append(netAddrHexStr);

		// get the port，4个字符
		uid.append(portHexStr);

		// get the random number，8个16进制字符
		uid.append(toHex(getRandom(), 8));

		return uid.toString();
	}

	private static String getSystemMillisRadix32() {
		String millisStr = Long.toString(System.currentTimeMillis(), 32).toUpperCase();
		int len = millisStr.length();
		if (len < 9) {
			StringBuffer buffer = new StringBuffer(10);
			buffer.append(millisStr);
			int offset = 9 - len;
			for (int i = 0; i < offset; i++) {
				buffer.append("0");
			}
			millisStr = buffer.toString();
		} else if (len > 9) {
			millisStr = millisStr.substring(len - 9);
		}
		return millisStr;
	}

	private static String getSeqRadix32() {
		String seqStr = Long.toString(getSeq32767(), 32).toUpperCase();
		int len = seqStr.length();
		if (len < 3) {
			StringBuffer buffer = new StringBuffer(3);
			int offset = 3 - len;
			for (int i = 0; i < offset; i++) {
				buffer.append("0");
			}
			buffer.append(seqStr);
			seqStr = buffer.toString();
		} else if (len > 3) {
			seqStr = seqStr.substring(len - 3);
		}
		return seqStr;
	}

	private static synchronized int getSeq32767() {
		int retv = nextSeq32767;
		nextSeq32767++;
		if (nextSeq32767 >= 32768) {
			nextSeq32767 = 0;
		}
		return retv;
	}

	private static int toInt(byte[] bytes) {
		int value = 0;
		int aryLen = bytes.length;
		for (int i = aryLen - 1; i >= 0; i--) {
			value <<= 8;
			value |= bytes[i] & 0x0FF;
		}

		return value;
	}

	private static String toHex(int value, int length) {
		StringBuffer buffer = new StringBuffer(length);
		int shift = length - 1 << 2;
		for (int i = -1; ++i < length;) {
			buffer.append(radixDigits[value >> shift & 0x0f]);
			value <<= 4;
		}

		return buffer.toString();
	}

	private static synchronized int getRandom() {
		return seederStatic.nextInt();
	}

	public static void main(String[] args) throws Exception {
	
		System.out.println(generate());
	}
}
