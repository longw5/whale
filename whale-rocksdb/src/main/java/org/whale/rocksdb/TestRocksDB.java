package org.whale.rocksdb;

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
import org.rocksdb.WriteBatch;
import org.rocksdb.WriteOptions;

public class TestRocksDB {

	static {
		RocksDB.loadLibrary();
	}

	private static final String db_path = "./rocksDbData";

	// test cf
	public static void main(String[] args) throws Exception {

		// open DB with two column families
		final List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>();
		
		// have to open default column family
		columnFamilyDescriptors
				.add(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, new ColumnFamilyOptions()));
		
		// open the new one, too
		columnFamilyDescriptors.add(new ColumnFamilyDescriptor("new_cf".getBytes(), new ColumnFamilyOptions()));
		final List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<>();
		try (final DBOptions options = new DBOptions();
				final RocksDB db = RocksDB.open(options, db_path, columnFamilyDescriptors, columnFamilyHandles)) {
			assert (db != null);

			try {
				// put and get from non-default column family
				db.put(columnFamilyHandles.get(0), new WriteOptions(), "key".getBytes(), "value".getBytes());

				// atomic write
				try (final WriteBatch wb = new WriteBatch()) {
					wb.put(columnFamilyHandles.get(0), "key2".getBytes(), "value2".getBytes());
					wb.put(columnFamilyHandles.get(1), "key3".getBytes(), "value3".getBytes());
					wb.remove(columnFamilyHandles.get(0), "key".getBytes());
					db.write(new WriteOptions(), wb);
				}

				// drop column family
				db.dropColumnFamily(columnFamilyHandles.get(1));
				
			} finally {
				for (final ColumnFamilyHandle handle : columnFamilyHandles) {
					handle.close();
				}
			}
		}
	}

	// test db
	public static void main1(String[] args) throws Exception {

		Options options = new Options().setCreateIfMissing(true).setAllowMmapWrites(true);
		options.setInfoLogLevel(InfoLogLevel.ERROR_LEVEL);
		RocksDB rocksDB = RocksDB.open(options, "./rocksDbData");

		rocksDB.put("11111111".getBytes(Charset.forName("UTF-8")),
				"222222222223333".getBytes(Charset.forName("UTF-8")));

		byte[] bs = rocksDB.get("11111111".getBytes(Charset.forName("UTF-8")));

		System.out.println(new String(bs, Charset.forName("UTF-8")));

	}
}
