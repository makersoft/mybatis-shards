package org.makersoft.shards.utils;

/**
 * 
 */
public class BytesHelper {
	private BytesHelper() {
	}

	public static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	public static int toInt(byte[] bytes, int startIndex) {
		int result = 0;
		for (int i = startIndex; i < 4; i++) {
			result = (result << 8) | (bytes[i] & 0xFF);
		}
		return result;
	}

	public static byte[] toBytes(int value) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (value >> 24);
		bytes[1] = (byte) ((value << 8) >> 24);
		bytes[2] = (byte) ((value << 16) >> 24);
		bytes[3] = (byte) ((value << 24) >> 24);
		return bytes;
	}
}
