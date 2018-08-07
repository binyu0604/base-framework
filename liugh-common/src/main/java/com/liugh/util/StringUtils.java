package com.liugh.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * 字符串工具类
 * 提供String的各种操作
 * @see
 * @version 1.0
 * @author gaoyr
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{

	/**
	 * 生成UUID
	 * 
	 * @return java.lang.String uuid
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 连接字符串
	 * 
	 * @param strs
	 *            被连接的字符串
	 * @return java.lang.String 已经连接的字符串
	 */
	public static String concat(Object... strs) {
		assert strs != null;

		StringBuilder builder = new StringBuilder();

		for (Object str : strs) {
			builder.append(str == null ? "null" : str.toString());
		}

		return builder.toString();
	}

	/**
	 * 连接字符串
	 * 
	 * @param sep
	 *            分隔符
	 * @param strs
	 *            被连接的字符串
	 * @return java.lang.String 连接好的字符串
	 */
	public static String concat(char sep, Object... strs) {
		assert strs != null;

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < strs.length; i++) {
			if (i > 0) {
				builder.append(sep);
			}
			builder.append(strs[i]);
		}

		return builder.toString();
	}

	/**
	 * 连接字符串
	 * 
	 * @param <T>
	 *            集合类型
	 * @param strs
	 *            被连接的字符串
	 * @return java.lang.String 已经连接的字符串
	 */
	public static <T extends Collection<?>> String concat(T strs) {
		assert strs != null;

		StringBuilder builder = new StringBuilder();

		for (Iterator<?> it = strs.iterator(); it.hasNext();) {
			builder.append(it.next());
		}

		return builder.toString();
	}

	/**
	 * 连接字符串
	 * 
	 * @param <T>
	 *            集合类型
	 * @param sep
	 *            分隔符
	 * @param strs
	 *            被连接的字符串
	 * @return java.lang.String 已经连接的字符串
	 */
	public static <T extends Collection<?>> String concat(char sep, T strs) {
		assert strs != null;

		StringBuilder builder = new StringBuilder();

		int i = 0;
		for (Iterator<?> it = strs.iterator(); it.hasNext(); i++) {
			if (i > 0) {
				builder.append(sep);
			}
			builder.append(it.next());
		}

		return builder.toString();
	}

	/**
	 * 判断是否为空字符串
	 * 
	 * @param target
	 *            被判断的字符串
	 * @return boolean 判断结果
	 */
	public static boolean isBlank(String target) {
		return target == null || target.trim().equals("")||target.trim().equals("null");
	}

	/**
	 * 判断是否为空字符串
	 * 
	 * @param targets
	 *            被判断的字符串
	 * @return boolean 判断结果
	 */
	public static boolean isBlank(String... targets) {
		int i = 0;

		for (String target : targets) {
			i = target == null || target.trim().equals("") ? i + 1 : i;
		}
		return i > 0;
	}

	/**
	 * 查看字符串的长度 如果字符串为空则返回-1 否则返回截去字符串两边空格的长度
	 * 
	 * @param str
	 *            字符串
	 * @return 字符串长度
	 * 
	 */
	public static int length(String str) {
		return str == null ? -1 : str.trim().length();
	}

	/**
	 * 获得一个字符序列的字节表示
	 * 
	 * @param sequence
	 *            字符序列
	 * @param charset
	 *            字符集
	 * @return byte[] 字节数组
	 */
	public static byte[] getBytes(CharSequence sequence, String charset) {
		try {
			return sequence.toString().getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("字符序列转换为字节失败", e);
			return null;
		}
	}

	/**
	 * 获得一个字符序列的字节表示
	 * 
	 * @param c
	 *            单个字符
	 * @param charset
	 *            字符集
	 * @return byte[] 字节数组
	 */
	public static byte[] getBytes(char c, String charset) {
		try {
			return String.valueOf(c).getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("字符序列转换为字节失败", e);
			return null;
		}
	}

	/**
	 * 查看字符串的字节长度 如果字符串为空则返回-1 否则返回截去字符串两边空格的长度
	 * 
	 * @param str
	 *            字符串
	 * @return 字符串长度
	 */
	public static int getBytesLength(String str) {
		return str == null ? -1 : str.trim().getBytes().length;
	}

	/**
	 * 截取字符串(根据提供的字符串获取位置)
	 * 
	 * @param target
	 *            原始字符串
	 * @param start
	 *            起始的字符串
	 * @return java.lang.String 截取后的字符串
	 */
	public static String substring(String target, String start) {
		assert !isBlank(target) && !isBlank(start);

		return substring(target, start, null);
	}

	/**
	 * 截取字符串(根据提供的字符串获取位置)
	 * 
	 * @param target
	 *            原始字符串
	 * @param start
	 *            起始的字符串
	 * @param end
	 *            结束的字符串
	 * @return java.lang.String 截取后的字符串
	 */
	public static String substring(String target, String start, String end) {
		assert target != null && start != null;
		if (end == null) {
			return substring(target, target.indexOf(start) + start.length(), target.length());
		} else {
			return substring(target, target.indexOf(start) + start.length(), target.indexOf(end));
		}
	}

	/**
	 * 截取字符串
	 * 
	 * @param target
	 *            原始字符串
	 * @param startIndex
	 *            起始索引
	 * @param endIndex
	 *            结束索引
	 * @return java.lang.String 截取后的字符串
	 */
	public static String substring(String target, int startIndex, int endIndex) {
		return target.substring(startIndex, endIndex);
	}

	/**
	 * 清理字符串左边空格
	 * 
	 * @param target
	 *            字符串
	 * @return java.lang.String 清理后的结果
	 */
	public static String trimLeft(String target) {
		return trimLeft(target, ' ');
	}

	/**
	 * 清理字符串左边指定字符
	 * 
	 * 
	 * @param target
	 *            字符串
	 * @param ignore
	 *            被忽视的字符
	 * @return java.lang.String 清理后的结果
	 */
	public static String trimLeft(String target, char ignore) {
		int len = target.length();
		int st = 0;
		int off = 0;
		char[] val = new char[target.length()];

		target.getChars(0, target.length(), val, 0);

		while ((st < len) && (val[off + st] == ignore)) {
			st++;
		}

		return ((st > 0) || (len < target.length())) ? target.substring(st, len) : target;
	}

	/**
	 * 清理字符串中边缘的指定字符
	 * 
	 * 
	 * @param target
	 *            字符串
	 * @param ignore
	 *            被忽视的字符
	 * @return java.lang.String 清理后的结果
	 */
	public static String trim(String target, char ignore) {
		int len = target.length();
		int st = 0;
		int off = 0;
		char[] val = new char[target.length()];

		target.getChars(0, target.length(), val, 0);

		while ((st < len) && (val[off + st] == ignore)) {
			st++;
		}
		while ((st < len) && (val[off + len - 1] == ignore)) {
			len--;
		}
		return ((st > 0) || (len < target.length())) ? target.substring(st, len) : target;
	}

	/**
	 * 清理字符串右边空格
	 * 
	 * @param target
	 *            字符串
	 * @return java.lang.String 清理后的结果
	 */
	public static String trimRight(String target) {
		return trimRight(target, ' ');
	}

	/**
	 * 清理字符串右边指定字符
	 * 
	 * @param target
	 *            字符串
	 * @param ignore
	 *            被忽视的字符
	 * @return java.lang.String 清理后的结果
	 */
	public static String trimRight(String target, char ignore) {
		int len = target.length();
		int st = 0;
		int off = 0;
		char[] val = new char[target.length()];

		target.getChars(0, target.length(), val, 0);

		while ((st < len) && (val[off + len - 1] == ignore)) {
			len--;
		}
		return ((st > 0) || (len < target.length())) ? target.substring(st, len) : target;
	}

	/**
	 * 判断指定字符串是否为汉字
	 * 
	 * @param target
	 *            字符串
	 * @return boolean 判断结果
	 */
	public static boolean isChineseCharacter(String target) {
		return target.length() != target.getBytes().length;
	}

	/**
	 * 将 String 转换为 application/x-www-form-urlencoded MIME
	 * 
	 * @param target
	 *            被编码的字符串
	 * @param charset
	 *            字符串的字符集
	 * @return java.lang.String 编码后的字符串
	 */
	public static String encode(String target, String charset) {
		try {
			return URLEncoder.encode(target, charset);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("将 String 转换为 application/x-www-form-urlencoded MIME失败", e);
			return null;
		}
	}

	/**
	 * 将 application/x-www-form-urlencoded MIME 转换为 String
	 * 
	 * @param target
	 *            被解码的字符串
	 * @param charset
	 *            字符串的字符集
	 * @return java.lang.String 解码后的字符串
	 */
	public static String decode(String target, String charset) {
		try {
			return URLDecoder.decode(target, charset);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("将 application/x-www-form-urlencoded MIME 转换为 String失败", e);
			return null;
		}
	}

	/**
	 * 将 String 转换为 application/x-www-form-urlencoded MIME(自定义 加密)
	 * 
	 * @param target
	 *            被编码的字符串
	 * @param charset
	 *            字符串的字符集
	 * @return java.lang.String 编码后的字符串
	 * @throws UnsupportedEncodingException
	 *             更换字符集失败
	 */
	public static String customEncode(String target, String charset) throws UnsupportedEncodingException {
		assert !isBlank(target) && !isBlank(charset);

		StringBuilder mergrd = new StringBuilder();
		StringBuilder encoded = new StringBuilder();

		byte[] bytes = target.getBytes(charset);

		for (int i = 0; i < bytes.length; i++) {
			mergrd.append(NumberUtils.toBinaryString(bytes[i], true));
		}

		int groupCount = mergrd.length() / FIVE_BIT;// 以5个bit为单位，计算能分多少组
		int lastCount = mergrd.length() % FIVE_BIT;// 计算余下的位数

		if (lastCount > 0) {// 如果存在余数 则增加一个组
			groupCount += 1;
		}

		int loopNum = groupCount * FIVE_BIT;// 循环所需的条件

		for (int i = 0; i < loopNum; i += FIVE_BIT) {// 每次递增5位来截取
			int end = i + FIVE_BIT;// 结束点
			boolean flag = false;// 标记是否到了余数的那一截

			if (end > mergrd.length()) {
				end = (i + lastCount);
				flag = true;
			}

			int intFiveBit = Integer.parseInt(mergrd.substring(i, end), BINARY);// 截取后从二进制转为十进制

			if (flag) {
				intFiveBit <<= (FIVE_BIT - lastCount);
			}

			encoded.append(CODEC_TABLE[intFiveBit]);// 利用该十进制数作为码表的索引获取对应的字符，并追加到encoded
		}

		return encoded.toString();
	}

	/**
	 * 将 application/x-www-form-urlencoded MIME 转换为 String(自定义 解密)
	 * 
	 * @param target
	 *            被解码的字符串
	 * @param charset
	 *            字符串的字符集
	 * @return java.lang.String 解码后的字符串
	 * @throws UnsupportedEncodingException
	 *             根据指定字符集重新创建字符串失败
	 */
	public static String customDecode(String target, String charset) throws UnsupportedEncodingException {
		assert !isBlank(target) && !isBlank(charset);

		StringBuilder binarys = new StringBuilder();

		int start = EIGHT_BIT - FIVE_BIT;
		for (int i = 0; i < target.length(); i++) {
			int index = -1;// 从码表里获取相应的索引

			for (int x = 0; x < CODEC_TABLE.length; x++) {
				if (target.charAt(i) == CODEC_TABLE[x]) {
					index = x;
					break;
				}
			}
			binarys.append(NumberUtils.toBinaryString((byte) index, true).substring(start));// 去掉多余的0并且追加到binarys
		}

		byte[] bytes = new byte[binarys.length() / EIGHT_BIT];// 按8个bit拆分,剩下的余数扔掉.扔掉的余数是在(编码/加密)的分割时候,在分剩的余数的后面补的0

		for (int i = 0, j = 0; i < bytes.length; i++) {
			j += EIGHT_BIT;
			bytes[i] = Integer.valueOf(binarys.substring(j - EIGHT_BIT, j), BINARY).byteValue();
		}

		return new String(bytes, charset);
	}

	/**
	 * 转换字符集
	 * 
	 * @param target
	 *            被转换的字符串
	 * @param charset
	 *            转换后的字符集
	 * @return java.lang.String 转换后的字符串
	 */
	public static String changeStringCharset(String target, String charset) {
		try {
			return new String(target.getBytes(charset), charset);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("转换字符集失败", e);
			return null;
		}
	}

	/**
	 * 将指定的字符串插入到原始字符串中
	 * 
	 * @param target
	 *            原始字符串
	 * @param toAdd
	 *            将被添加的字符串
	 * @param index
	 *            添加到的位置
	 * @return java.lang.String 完成添加后的字符串
	 */
	public static String insertStr(String target, String toAdd, int index) {
		assert target != null;
		assert toAdd != null;
		assert index >= 0 && index < target.length();

		char[] chars = new char[target.length() + toAdd.length()];

		toAdd.getChars(0, toAdd.length(), chars, index);
		target.getChars(0, index, chars, 0);
		target.getChars(index, target.length(), chars, index + toAdd.length());

		return new String(chars);
	}
	/**
	 * 生成邀请码
	 * 
	 * @param rankingNum 排名
	 *            rankingNum
	 * @return String
	 */
	public static String getInviteCode(long rankingNum) {
		StringBuffer sb = new StringBuffer();
		String endStr = Long.toHexString(rankingNum + 1000).toUpperCase();
		int tempLength = 8;
		if (endStr.length() < tempLength) {
			int forC = tempLength - endStr.length();
			for (int i = 0; i < forC; i++) {
				sb.append(new Random().nextInt(10));
			}
		}
		sb.append(endStr);
		return sb.toString();
	}
	/**
	 * 获取字符型字节数 数字和字母占一个字节，汉字占2个字节
	 * @param str
	 * @return
	 */
	public static int getStrLength(String str){
		int length =0;
		if(StringUtils.isBlank(str))
			return length;
		for (int i = 0; i < str.length(); i++) {
			if(Character.isDigit(str.charAt(i))||(str.charAt(i) <= 'Z' && str.charAt(i) >= 'A')
                    || (str.charAt(i) <= 'z' && str.charAt(i) >= 'a')){
				length = length+1;
			}else{
				length =length+2;
			}
		}
		return length;
	}
	// 常量属性
	/** 日志对象 */
	public static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);

	// URLEncode常量表 start
	/** 码表 */
	private static final char[] CODEC_TABLE = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z', '2', '3', '4', '5', '6', '7' };

	/** 表示5bit的字节 */
	public static final int FIVE_BIT = 5;

	/** 表示8bit的字节 */
	public static final int EIGHT_BIT = 8;

	/** 表示二进制 */
	public static final int BINARY = 2;

	// URLEncode常量表 end
	public static void main(String[] args) {
		//System.out.println(StringUtils.getInviteCode(1));
//		System.out.println(new Date("2016-11-18 09:00:00").getTime());
//		Long l = 1479293071339L;
//		System.out.println(DateUtils.DateFormatUnit.DATE_TIME.getDateStr(new Date(l)));
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("1", "1");
		map.put("1", "1");
		if(map.get("3") == null){
			
		}
	}
}
