package com.beagledata.hubble.util;

public class SqlTrimBlankUtil {

	public static final String BLANK_SPACE="\\s{2,}";
	public static final String ONE_SPACE=" ";
	
	/**
	 * 2个以上空格转1个空格
	 * 正则匹配多个空格
	 */
	public static String sqlTrimBlank(String text) {
		return text.replaceAll(BLANK_SPACE, ONE_SPACE);
	}
	
}
