package org.whale.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * udf 函数
 * @author Administrator
 */
public class TestUdf extends UDF {

	public String evaluate(String s) {
		String all = s.replaceAll("_", ":");
		return all;
	}
}
