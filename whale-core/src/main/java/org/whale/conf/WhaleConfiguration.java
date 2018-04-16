package org.whale.conf;

import org.apache.hadoop.conf.Configuration;
import org.whale.util.Constant;

public class WhaleConfiguration {

	private final static Configuration conf = new Configuration();
	
	static {
		conf.set("fs.defaultFS", Constant.DEFAULT_HDFS_URL);
		conf.set("fs.hdfs.impl", Constant.DEFAULT_FS);  
	}

	public WhaleConfiguration() {
		super();
	}

	public static Configuration getConf() {
		return conf;
	}
}	
