package org.whale.bulkload;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.rocksdb.ColumnFamilyDescriptor;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.ColumnFamilyOptions;
import org.rocksdb.DBOptions;
import org.rocksdb.InfoLogLevel;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
import org.rocksdb.WriteBatch;
import org.rocksdb.WriteOptions;

public class TestRocksDB {

	static {
		RocksDB.loadLibrary();
	}

	private static final String db_path = "G:\\hubble1012\\schemas\\database";

	public static void main(String[] args) throws Exception {

		Options options = new Options().setCreateIfMissing(true).setAllowMmapWrites(true);
		options.setInfoLogLevel(InfoLogLevel.ERROR_LEVEL);
		
		options.setAllow2pc(true)
		.setInfoLogLevel(InfoLogLevel.ERROR_LEVEL)
		.setAllowFAllocate(false)
		.setCreateIfMissing(true);
		options.optimizeForSmallDb();
		
		RocksDB rocksDB = RocksDB.openReadOnly(options, db_path);

		RocksIterator newIterator = rocksDB.newIterator();
		
		byte[] value = newIterator.value();
		
		
		System.out.println(new String(value));
	
		System.out.println("aaaaaaaaaaaaaaa");
	}
	

}
