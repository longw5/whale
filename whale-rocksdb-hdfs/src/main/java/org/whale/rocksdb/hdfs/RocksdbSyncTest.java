package org.whale.rocksdb.hdfs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.rocksdb.ColumnFamilyDescriptor;
import org.rocksdb.Env;
import org.rocksdb.FlushOptions;
import org.rocksdb.HdfsEnv;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.TransactionDB;
import org.rocksdb.WriteOptions;

/**
 * 
 * @author qiaolong
 *
 */
public class RocksdbSyncTest {

	static {
		RocksDB.loadLibrary();
	}
	
	public static String value = "adhlfadfajdfl;audfpoiqeurqenfpoqidfandfkal;ufdajdfa;ldfuaidfjal;kdfujalfdjal;";
	public static void main(String[] args) {
		String usage = "Usage : RocksdbTest <count> <path>";
		
		int count = Integer.parseInt("2");
		String path = "G:\\aaaa";
		
//		try(Env env = new HdfsEnv("default")){
		try(Env env = Env.getDefault()){
			try (final Options options = new Options()
					.setWalTtlSeconds(24 * 60)
					.setWalSizeLimitMB(256)
					.setEnv(env).setCreateIfMissing(true)) {
				
				try (final RocksDB db = TransactionDB.open(options,path)) {
					try(WriteOptions wo = new WriteOptions()
							.setDisableWAL(false)
							.setNoSlowdown(false)
							.setSync(true)){
						for(int i = 0;i < count;i ++) {
							db.put(wo,UUID.randomUUID().toString().getBytes(), (value + i).getBytes());
							if(i % 10000 == 0) {
								System.out.println("count == " + i);
							}
						}
					}
					
					long start = System.currentTimeMillis();
					try (FlushOptions flush = new FlushOptions().setWaitForFlush(true)) {
						db.flush(flush);
					}
					long end = System.currentTimeMillis();
					System.out.println("flush time == " + (end - start));
				}
			} catch (RocksDBException e) {
				e.printStackTrace();
			}
		}
	}
}
