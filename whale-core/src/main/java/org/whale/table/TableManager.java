package org.whale.table;

public interface TableManager {

	/**
	 * 上线指定的表
	 * @param table
	 * @return
	 */
	boolean isTableOnline(TableName table);
	
	/**
	 * 下线指定的表
	 * @param tableName
	 * @return
	 */
	boolean isTableOffLine(TableName tableName);
	
	boolean onLineTable(TableName tableName);
	
	boolean offLineTable(TableName tableName);
	
}
