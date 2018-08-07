package com.liugh.util;

import java.util.Arrays;
import java.util.List;

/**
 * 数字工具类
 * 
 * @see 对数字的截取、转换操作
 * @version 1.0
 * @author gaoyr
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils{
	 public static int toInt(final Integer teger) {
		 if(teger == null){
			 return 0;
		 }
	     return teger;
	    }
	/**
	 * 四舍五入到指定位数
	 * 
	 * @param source
	 *            浮点数
	 * @param decimalDigits
	 *            小数位数
	 * @return double 截取结果
	 */
	public static double round(double source, int decimalDigits) {
		assert decimalDigits >= 0;
		int multiple = (int) Math.pow(ACCURACY_BY_ROUND, decimalDigits);
		return (double) Math.round(source * multiple) / multiple;
	}

	/**
	 * 将字节转换为二进制
	 * 
	 * @param source
	 *            单个字节
	 * @param isFormat
	 *            是否进行格式化 保证每次转换后必然会返回一个八位字节 可能填充和消减多余的位
	 * 
	 * @return java.lang.String 二进制表达
	 */
	public static String toBinaryString(byte source, boolean isFormat) {
		if (isFormat) {
			String result = Integer.toBinaryString(source);

			if (source < 0) {
				return result.substring(INT_BIT_LENGTH - BYTE_BIT_LENGTH, result.length());
			} else {
				if (result.length() == BYTE_BIT_LENGTH) {
					return result;
				} else {
					char[] chars = new char[BYTE_BIT_LENGTH - result.length()];
					Arrays.fill(chars, '0');

					return new String(chars) + result;
				}
			}
		} else {
			return Integer.toBinaryString(source);
		}
	}

	/**
	 * 将整型转换为汉字
	 * 
	 * @param number
	 *            被转换的数字
	 * @return java.lang.String 转换后的汉字
	 */
	public static String transformChineseNumber(int number) {
		return transformChineseNumber(new Long(number).longValue(), 0);
	}

	/**
	 * 将整型转换为汉字
	 * 
	 * @param number
	 *            被转换的数字
	 * @param depth
	 *            数字单位(为0的话没有单位 1为万 2为亿)
	 * @return java.lang.String 转换后的汉字
	 */
	public static String transformChineseNumber(long number, int depth) {
		assert depth >= 0;

		String chinese = "";
		String src = String.valueOf(number);

		if (src.endsWith("L") || src.endsWith("l")) {
			src = src.substring(0, src.length() - 1);
		}

		if (src.length() > N_RANGE) {
			chinese = transformChineseNumber(Integer.parseInt(src.substring(0, src.length() - N_RANGE)), depth + 1);
			chinese += transformChineseNumber(Integer.parseInt(src.substring(src.length() - N_RANGE, src.length())), depth - 1);
		} else {
			char prv = 0;
			for (int i = 0; i < src.length(); i++) {
				switch (src.charAt(i)) {
				case '0':
					if (prv != '0') {
						chinese += "零";
					}
					break;
				case '1':
					chinese += "壹";
					break;
				case '2':
					chinese += "贰";
					break;
				case '3':
					chinese += "叁";
					break;
				case '4':
					chinese += "肆";
					break;
				case '5':
					chinese += "伍";
					break;
				case '6':
					chinese += "陆";
					break;
				case '7':
					chinese += "柒";
					break;
				case '8':
					chinese += "捌";
					break;
				case '9':
					chinese += "玖";
					break;
				}
				prv = src.charAt(i);

				if (prv != '0') {
					switch (src.length() - 1 - i) {
					case N_UNIT_TEN:
						chinese = chinese + "拾";
						break;
					case N_UNIT_HUNDRED:
						chinese = chinese + "佰";
						break;
					case N_UNIT_THOUSAND:
						chinese = chinese + "仟";
						break;
					}
				}
			}
		}
		while ((chinese.length() > 0) && (chinese.lastIndexOf("零") == chinese.length() - 1)) {
			chinese = chinese.substring(0, chinese.length() - 1);
		}

		switch (depth) {
		case N_UNIT_WAN:
			chinese += "万";
			break;
		case N_UNIT_BILLION:
			chinese += "亿";
			break;
		}

		return chinese;
	}
	/**
	 * list排序
	 * @param list
	 * @return
	 */
	public static List<Integer> sortList(List<Integer> list){
		int temp=0;
	    for(int i=0;i<list.size();i++){
	       for(int j=i+1;j<list.size();j++){
	    	   if(list.get(i)>list.get(j)){
		           temp=list.get(i);
		           list.add(i, list.get(j));
		           list.add(j, temp);
	    	   }
	       }
	    }
	    return list;
	}
	
	public static double subDouble(double n) {
		
		String n1 = (n+"").split("\\.")[0];
		String n2 = (n+"").split("\\.")[1];
		if(n2.length()>6) {
			n2 = n2.	substring(0, 6);
		}
		n = Double.valueOf(n1+"."+n2);
		return n;
		
	}
	// 常量属性

	/** 四舍五入时用到的常量 */
	private static final int ACCURACY_BY_ROUND = 10;

	/** 字节中存在的位数 */
	private static final int BYTE_BIT_LENGTH = 8;

	/** 整型中存在的位数 */
	private static final int INT_BIT_LENGTH = 32;

	/** 数字区间范围 */
	private static final int N_RANGE = 4;

	/** 数字单位常量(十) */
	private static final int N_UNIT_TEN = 1;

	/** 数字单位常量(百) */
	private static final int N_UNIT_HUNDRED = 2;

	/** 数字单位常量(千) */
	private static final int N_UNIT_THOUSAND = 3;

	/** 数字单位常量(万) */
	private static final int N_UNIT_WAN = 1;

	/** 数字单位常量(亿) */
	private static final int N_UNIT_BILLION = 2;

	// 数字常量 由于魔法数字的缘故 通过这些常量组合到一起代替以前的字面常量..
	/** 数字常量0 */
	public static final int INT_0 = 0;

	/** 数字常量1 */
	public static final int INT_1 = 1;

	/** 数字常量2 */
	public static final int INT_2 = 2;

	/** 数字常量3 */
	public static final int INT_3 = 3;

	/** 数字常量4 */
	public static final int INT_4 = 4;

	/** 数字常量5 */
	public static final int INT_5 = 5;

	/** 数字常量6 */
	public static final int INT_6 = 6;

	/** 数字常量7 */
	public static final int INT_7 = 7;

	/** 数字常量8 */
	public static final int INT_8 = 8;

	/** 数字常量9 */
	public static final int INT_9 = 9;

	/** 数字常量10 */
	public static final int INT_10 = 10;
}
