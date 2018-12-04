package org.whale.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class Constant {

	//默认字符集
	public static String DEFAULT_CHARSET = "utf-8";
	
	//绑定主机ip
	public static String DEFAULT_HOST = "192.168.1.60";
	
	//绑定主机端口
	public static int DEFAULT_PORT = 9999;

	//hdfs url
	public static String DEFAULT_HDFS_URL = "hdfs://hadoop:8020";

	//fs
	public static String DEFAULT_FS = "org.apache.hadoop.hdfs.DistributedFileSystem";
	
	//索引路径
	public static String INDEX_PATH = "/lucene/nginx";
	
	//解析器
	public static Analyzer DEFAULT_ANALYZER = new StandardAnalyzer();
}
