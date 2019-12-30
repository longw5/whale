package com.whale.testhdfs;

import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.Env;
import org.rocksdb.HdfsEnv;
import org.rocksdb.InfoLogLevel;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksIterator;

public class TestHdfs {

	static {
		RocksDB.loadLibrary();
	}
	
	public static void main(String[] args) throws Exception {
		
		Env env = new HdfsEnv("default");
		Options options = new Options().setEnv(env).setInfoLogLevel(InfoLogLevel.ERROR_LEVEL);
		RocksDB rocksDB = RocksDB.openReadOnly(options, "hdfs://node1:9000/hubble1003/data/2B0V6CPYZ9C0/2C1DVADJ1A0W/2C1DVAG5XF5S/store");
		
		RocksIterator rows = rocksDB.newIterator();
		rows.seekToFirst();
		while (rows.isValid()) {
			System.out.println("=================================================================================");
			System.out.println("key == " + new String(rows.key()));
			rows.next();
		}
		
		rows.close();
		rocksDB.close();
		rows.close();
		
		options.close();
		env.close();
		System.out.println("==================================== Flush end ==================================");
		
		
	}
	
}
