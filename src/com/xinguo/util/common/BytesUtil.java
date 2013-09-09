package com.xinguo.util.common;

/**
 * 字节流工具类
 * 
 * createtime:2011-5-12
 * 
 * @author AoMing Yang
 */
/**
 * @author yangaoming
 *
 */
/**
 * @author yangaoming
 *
 */
/**
 * @author yangaoming
 *
 */
/**
 * @author yangaoming
 * 
 */
public class BytesUtil {
	/***************************************************************************
	 * 
	 * @param src1
	 *            保存的源byte[]
	 * @param src2
	 *            保存的源byte[]
	 * @return 将src1和src2拼接的byte
	 */
	public static byte[] addBytes(byte[] src1, byte[] src2) {
		byte[] dest = new byte[src1.length + src2.length];
		System.arraycopy(src1, 0, dest, 0, src1.length);
		System.arraycopy(src2, 0, dest, src1.length, src2.length);
		return dest;
	}

	/***************************************************************************
	 * 
	 * @param maxlength
	 *            目标byte的max长度
	 * @param src
	 *            每一个byte[]
	 * @return 目标byte[]数组
	 */
	public static byte[] addBytes(int maxlength, byte[]... src) {
		int length = 0; // 获取每一byte数组的长
		int index = 0; // 获取复制到目标数组的起始点，
		byte[] dest = new byte[maxlength]; // 目标数组
		for (int i = 0; i < src.length; i++) {
			length = src[i].length;
			System.arraycopy(src[i], 0, dest, index, length); // 将每一个byte[]
			// 复制到 目标数组
			index = index + length; // 起始位置向后挪动byte[]的length
		}

		// 目标长度的数组不知道长度，在长度不足的情况下，会向后补0，
		// 所以需要对得到的原始数组做一些处理

		// int count = 0;
		// for (int i = 0; i < dest.length; i++) {
		// if (dest[i] == 0) {
		//
		// count++; // 统计原始数组补0的个数
		// }
		// }
		// byte[] result = new byte[maxlength - count]; // 生成新数组保存我们需要的值（非补0）
		// int pos = 0;
		// for (int i = 0; i < result.length; i++) {
		// if (dest[i] != 0) { // 判断是非为0，将不为0的值保存
		// result[pos] = dest[i];
		// pos++;
		// }
		// }
		return dest;
	}

	/**
	 * 将short转为低字节在前，高字节在后的byte数组
	 * 
	 * @param src
	 * @param s
	 * @param index
	 */
	public static void putShort(byte src[], short s, int index) {
		src[index] = (byte) (s >> 8);
		src[index + 1] = (byte) (s >> 0);
	}

	/**
	 * 将short转为高字节在前，低字节在后的byte数组
	 * 
	 * @param src
	 * @param s
	 * @param index
	 */
	public static void putReverseBytesShort(byte[] src, short s, int index) {
		src[index] = (byte) (s >> 0);
		src[index + 1] = (byte) (s >> 8);
	}

	/**
	 * 将低字节在前，高字节在后的byte数组 转换为short
	 * 
	 * @param src
	 * @param index
	 * @return
	 */
	public static short getShort(byte[] src, int index) {
		return (short) (((src[index] << 8) | src[index + 1] & 0xff));
	}

	/**
	 * 将高字节在前，低字节在后的byte数组 转换为short
	 * 
	 * @param src
	 * @param index
	 * @return
	 */
	public static short getReverseBytesShort(byte[] src, int index) {
		return (short) (((src[index + 1] << 8) | src[index] & 0xff));
	}

	/**
	 * 将int转为低字节在前，高字节在后的byte数组
	 * 
	 * @param src
	 * @param s
	 * @param index
	 */
	public static void putInt(byte[] target, int value, int index) {
		target[index + 0] = (byte) (value >> 24);
		target[index + 1] = (byte) (value >> 16);
		target[index + 2] = (byte) (value >> 8);
		target[index + 3] = (byte) (value >> 0);
	}

	/**
	 * 将int转为高字节在前，低字节在后的byte数组
	 * 
	 * @param src
	 * @param s
	 * @param index
	 */
	public static void putReverseBytesInt(byte[] target, int value, int index) {
		target[index + 3] = (byte) (value >> 24);
		target[index + 2] = (byte) (value >> 16);
		target[index + 1] = (byte) (value >> 8);
		target[index + 0] = (byte) (value >> 0);
	}

	/**
	 * 将低字节在前，高字节在后的byte数组 转换为int
	 * 
	 * @param src
	 * @param index
	 * @return
	 */
	public static int getInt(byte[] target, int index) {
		return (int) ((((target[index + 0] & 0xff) << 24)
				| ((target[index + 1] & 0xff) << 16)
				| ((target[index + 2] & 0xff) << 8) | ((target[index + 3] & 0xff) << 0)));
	}

	/**
	 * 将高字节在前，低字节在后的byte数组 转换为int
	 * 
	 * @param src
	 * @param index
	 * @return
	 */
	public static int getReverseBytesInt(byte[] target, int index) {
		return (int) ((((target[index + 3] & 0xff) << 24)
				| ((target[index + 2] & 0xff) << 16)
				| ((target[index + 1] & 0xff) << 8) | ((target[index + 0] & 0xff) << 0)));
	}

	/**
	 * 将long转为低字节在前，高字节在后的byte数组
	 * 
	 * @param src
	 * @param s
	 * @param index
	 */
	public static void putLong(byte[] target, long value, int index) {
		target[index + 0] = (byte) (value >> 56);
		target[index + 1] = (byte) (value >> 48);
		target[index + 2] = (byte) (value >> 40);
		target[index + 3] = (byte) (value >> 32);
		target[index + 4] = (byte) (value >> 24);
		target[index + 5] = (byte) (value >> 16);
		target[index + 6] = (byte) (value >> 8);
		target[index + 7] = (byte) (value >> 0);
	}

	/**
	 * 将long转为高字节在前，低字节在后的byte数组
	 * 
	 * @param src
	 * @param s
	 * @param index
	 */
	public static void putReverseBytesLong(byte[] target, long value, int index) {
		target[index + 7] = (byte) (value >> 56);
		target[index + 6] = (byte) (value >> 48);
		target[index + 5] = (byte) (value >> 40);
		target[index + 4] = (byte) (value >> 32);
		target[index + 3] = (byte) (value >> 24);
		target[index + 2] = (byte) (value >> 16);
		target[index + 1] = (byte) (value >> 8);
		target[index + 0] = (byte) (value >> 0);
	}

	/**
	 * 将低字节在前，高字节在后的byte数组 转换为long
	 * 
	 * @param src
	 * @param index
	 * @return
	 */
	public static long getLong(byte[] target, int index) {
		return ((((long) target[index + 0] & 0xff) << 56)
				| (((long) target[index + 1] & 0xff) << 48)
				| (((long) target[index + 2] & 0xff) << 40)
				| (((long) target[index + 3] & 0xff) << 32)
				| (((long) target[index + 4] & 0xff) << 24)
				| (((long) target[index + 5] & 0xff) << 16)
				| (((long) target[index + 6] & 0xff) << 8) | (((long) target[index + 7] & 0xff) << 0));
	}

	/**
	 * 将高字节在前，低字节在后的byte数组 转换为long
	 * 
	 * @param src
	 * @param index
	 * @return
	 */
	public static long getReverseBytesLong(byte[] target, int index) {
		return ((((long) target[index + 7] & 0xff) << 56)
				| (((long) target[index + 6] & 0xff) << 48)
				| (((long) target[index + 5] & 0xff) << 40)
				| (((long) target[index + 4] & 0xff) << 32)
				| (((long) target[index + 3] & 0xff) << 24)
				| (((long) target[index + 2] & 0xff) << 16)
				| (((long) target[index + 1] & 0xff) << 8) | (((long) target[index + 0] & 0xff) << 0));
	}
}
