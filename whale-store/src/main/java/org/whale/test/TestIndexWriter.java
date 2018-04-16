package org.whale.test;

import java.io.File;

import org.whale.index.WhaleIndexWriter;
import org.whale.record.WhaleWriter;
import org.whale.table.TableDesc;

public class TestIndexWriter {

	static final TableDesc tableDesc = new TableDesc();
	static {
		tableDesc.setDatabase("dbName");
		tableDesc.setTableName("tblName");
		tableDesc.setFilePath("/whale/tblName");
	}
	
	public static void main(String[] args) throws Exception {
		WhaleIndexWriter indexWriter = new WhaleIndexWriter(tableDesc.getFilePath());
		WhaleWriter writer = new WhaleWriter(indexWriter);
		writer.insert(new File("E://lucene/nginx1"));
		System.out.println("ok.............");
	}
}
