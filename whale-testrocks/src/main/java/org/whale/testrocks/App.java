package org.whale.testrocks;

import org.rocksdb.HdfsEnv;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;

/**
 * Hello world!
 *
 */
public class App {

	static {
		RocksDB.loadLibrary();
	}

	public static void main(String[] args) throws Exception {
		
//		Options options = new Options().setEnv(new HdfsEnv("default")).setCreateIfMissing(true);
		 Options options = new Options().setCreateIfMissing(true);
		// RocksDB db = RocksDB.open(options, "aaaa");
		RocksDB db = RocksDB.open(options, "aaaa");
		try {
			db.put("aa".getBytes(), "aa".getBytes());
		} finally {
			db.close();
		}
	}
}
