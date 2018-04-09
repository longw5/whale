package org.whale.schame;

import java.util.HashMap;
import java.util.Map;

import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.whale.data.MemoryData;
import org.whale.data.MemoryData.Database;
import org.whale.table.MemoryTable;

public class WhaleSchame extends AbstractSchema {

	private String dbName;

	public WhaleSchame(String dbName) {
		super();
		this.dbName = dbName;
	}
	
	@Override
	public Map<String, Table> getTableMap() {
		
		Map<String, Table> tables = new HashMap<>();
		
		Database database = MemoryData.MAP.get(this.dbName);
		
		if(database == null) {
			return tables;
		}

		for (MemoryData.Table table : database.tables) {
			tables.put(table.name, new MemoryTable(table));
		}
		return tables;
	}
	
	@Override
	public String toString() {
		return "WhaleSchame [dbName=" + dbName + "]";
	}

	public static void main(String[] args) {
		
		WhaleSchame schame = new WhaleSchame("school");
		
		System.out.println(schame.getTableMap());
		
	}
	
}
