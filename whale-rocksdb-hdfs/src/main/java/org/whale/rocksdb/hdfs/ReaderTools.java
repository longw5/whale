package org.whale.rocksdb.hdfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.rocksdb.ColumnFamilyDescriptor;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.DBOptions;
import org.rocksdb.Env;
import org.rocksdb.HdfsEnv;
import org.rocksdb.InfoLogLevel;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;



/**
 * compact 指定的路径
 * @author Qiaolong
 *
 */
public class ReaderTools implements Tools{
	public static final String ESTIMATE_TOTAL_KEY = "rocksdb.estimate-num-keys";
	static {
		RocksDB.loadLibrary();
	}
	
	public ReaderTools() {
	}
	
	public static void main(String[] args) throws RocksDBException, IOException {
		String usage = "Usage:Reader <hdfs|local> <dir>";
		
		String type = "local";
		String dir = "G:\\aaaa";
		
		Env env = null;
		if("hdfs".equalsIgnoreCase(type)) {
			env = new HdfsEnv("default");
		}else if("local".equalsIgnoreCase(type)){
			env = Env.getDefault();
		}
		
		Options options = new Options().setEnv(env).setInfoLogLevel(InfoLogLevel.ERROR_LEVEL);
		List<byte[]> cfs = RocksDB.listColumnFamilies(options, dir);
		
		List<ColumnFamilyDescriptor> cfds = new ArrayList<>();
		if(cfs != null && !cfs.isEmpty()) {
			for(byte[] cf : cfs) {
				cfds.add(new ColumnFamilyDescriptor(cf));
			}
		}
		
		if(cfds.isEmpty()) {
			cfds.add(new ColumnFamilyDescriptor("default".getBytes()));
		}
		
		DBOptions dbOptions = new DBOptions().setEnv(env).setInfoLogLevel(InfoLogLevel.ERROR_LEVEL);
		List<ColumnFamilyHandle> cfhs = new ArrayList<>();
		
		System.out.println("Will open db at " + dir);
		RocksDB rocksDB = RocksDB.openReadOnly(dbOptions,dir, cfds, cfhs);
		
		System.out.println("==================================== Flush start ==================================");
		
		System.out.println("estimateCount = " + rocksDB.getLongProperty(ESTIMATE_TOTAL_KEY));
		
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
		if(cfhs != null) {
			for(ColumnFamilyHandle cfh : cfhs){
				cfh.close();
 			}
		}
		
		options.close();
		dbOptions.close();
		env.close();
		System.out.println("==================================== Flush end ==================================");
	}

}
