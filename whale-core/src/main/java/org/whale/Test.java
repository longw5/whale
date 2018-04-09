package org.whale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.apache.calcite.jdbc.CalciteConnection;

public class Test {

	public static void main(String[] args) throws Exception {

		Class.forName("org.apache.calcite.jdbc.Driver");
		
		Properties info = new Properties();

		// 存的meta信息，数据库名称
		Connection connection = DriverManager.getConnection("jdbc:calcite:model=" + "meta/School.json", info);
		// 加载连接类
		CalciteConnection calciteConn = connection.unwrap(CalciteConnection.class);

		Statement statement = calciteConn.createStatement();
		
		ResultSet executeQuery = statement.executeQuery("select * from company");
		
		while(executeQuery.next()) {
			
			String db1 = executeQuery.getString(1);
			String db2 = executeQuery.getString(2);
			String db3 = executeQuery.getString(3);
			System.out.println(db1+":"+db2+":"+db3);
		}
	}

	public static void main2(String[] args) throws Exception {

		Properties info = new Properties();
		info.put("model",
				"inline:" + "{\n" + "  version: '1.0',\n" + "  defaultSchema: 'school',\n" + "   schemas: [\n"
						+ "     {\n" + "       type: 'custom',\n" + "       name: 'school',\n"
						+ "       factory: 'org.whale.schame.WhaleSchameFactory',\n" + "       operand: {\n"
						+ "         directory: '/does/not/exist'\n" + "       }\n" + "     }\n" + "   ]\n" + "}");

		Connection connection = DriverManager.getConnection("jdbc:calcite:", info);

		CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
	}

	public static void main1(String[] args) throws Exception {

		Class.forName("org.apache.calcite.jdbc.Driver");
		Properties info = new Properties();

		// 存的meta信息，数据库名称
		Connection connection = DriverManager.getConnection("jdbc:calcite:model=" + "meta/School.json", info);
		// 加载连接类
		CalciteConnection calciteConn = connection.unwrap(CalciteConnection.class);

		System.out.println(calciteConn.getSchema());

		System.out.println(calciteConn.getTypeFactory());

		System.out.println(calciteConn.getCatalog());

		System.out.println(calciteConn.getMetaData().getDriverName());

		System.out.println(calciteConn.getProperties());

	}

}
