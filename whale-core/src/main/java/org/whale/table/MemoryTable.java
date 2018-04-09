package org.whale.table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.calcite.DataContext;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.linq4j.AbstractEnumerable;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeName;
import org.whale.data.MemoryData;
import org.whale.data.MemoryData.Table;
import org.whale.data.MemoryEnumerator;
import org.whale.schame.WhaleSchame;

public class MemoryTable extends AbstractTable implements ScannableTable{

	private MemoryData.Table table;
	
	private RelDataType dataType;
	
	public MemoryTable(Table table) {
		this.table = table;
		dataType = null;
	}

	@Override
	public RelDataType getRowType(RelDataTypeFactory typeFactory) {

		if(dataType == null) {
			
			RelDataTypeFactory.FieldInfoBuilder fieldInfoBuilder = typeFactory.builder();
			
			for (MemoryData.Column column : table.columns) {
				fieldInfoBuilder.add(column.name, SqlTypeName.VARCHAR);
			}
			this.dataType = typeFactory.createStructType(fieldInfoBuilder);
		}
		return this.dataType;
	}

	@Override
	public Enumerable<Object[]> scan(DataContext root) {
		
		final List<String> types = new ArrayList<String>(table.columns.size());
		
		for(MemoryData.Column column : table.columns) {
			types.add(column.type);
		}
		
        final int[] fields = identityList(this.dataType.getFieldCount());
		
		return new AbstractEnumerable<Object[]>() {

			@Override
			public Enumerator<Object[]> enumerator() {
				return new MemoryEnumerator<Object[]>(fields, types, table.data);
			}
		};
	}
	
	private static int[] identityList(int n) {
        int[] integers = new int[n];
        for (int i = 0; i < n; i++) {
            integers[i] = i;
        }
        return integers;
    }
	
	@Override
	public String toString() {
		return "MemoryTable [table=" + table + ", dataType=" + dataType + "]";
	}

	public static void main(String[] args) throws Exception {	
		
		Class.forName("org.apache.calcite.jdbc.Driver");
		Properties info = new Properties();

		// 存的meta信息，数据库名称
		Connection connection = DriverManager.getConnection("jdbc:calcite:model=" + "meta/School.json", info);
		// 加载连接类
		CalciteConnection calciteConn = connection.unwrap(CalciteConnection.class);
		
		WhaleSchame schame = new WhaleSchame("school");
		Map<String, org.apache.calcite.schema.Table> tableMap = schame.getTableMap();
		
		Set<Entry<String, org.apache.calcite.schema.Table>> entrySet = tableMap.entrySet();
		
		for (Entry<String, org.apache.calcite.schema.Table> entry : entrySet) {
			System.out.println(entry.getKey() + ":" + entry.getValue().getRowType(calciteConn.getTypeFactory()));
		}
	}
}