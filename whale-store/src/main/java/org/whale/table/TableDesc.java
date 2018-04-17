package org.whale.table;

import java.util.Arrays;

public class TableDesc {

	private String database;
	private String tableName;
	private int[] types;
	private String[] fields; 
	private String filePath;
	
	public TableDesc(String database, String tableName, int[] types, String[] fields, String filePath) {
		super();
		this.database = database;
		this.tableName = tableName;
		this.types = types;
		this.fields = fields;
		this.filePath = filePath;
	}

	public TableDesc() {
		super();
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int[] getTypes() {
		return types;
	}

	public void setTypes(int[] types) {
		this.types = types;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "TableDesc [database=" + database + ", tableName=" + tableName + ", types=" + Arrays.toString(types)
				+ ", fields=" + Arrays.toString(fields) + ", filePath=" + filePath + "]";
	}
}
