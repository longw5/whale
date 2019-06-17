package org.whale.rocksdb;
import java.nio.charset.Charset;

import org.rocksdb.InfoLogLevel;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;

public class TestRocksDB {

    public static void main(String [] args) throws Exception {


        RocksDB.loadLibrary();
        Options options = new Options().setCreateIfMissing(true).setAllowMmapWrites(true);
        options.setInfoLogLevel(InfoLogLevel.ERROR_LEVEL);
        RocksDB rocksDB = RocksDB.open(options, "./rocksDbData");

        rocksDB.put("11111111".getBytes(Charset.forName("UTF-8")), "22222222222".getBytes(Charset.forName("UTF-8")));
        
        byte[] bs = rocksDB.get("11111111".getBytes(Charset.forName("UTF-8")));
        
        System.out.println(new String(bs, Charset.forName("UTF-8")));
        
    }
}
