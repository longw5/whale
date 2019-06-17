package org.whale.hubble;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class main3 {

	public static void main(String[] args) {
		// 声明Connection对象
		Connection con;
		// 驱动程序名
		String driver = "com.beagledata.hubble.jdbc.HubbleDriver";
		// URL指向要访问的数据库名mydata
		String url = "jdbc:hubble://192.168.118.101:30008/hubble";
		// MySQL配置时的用户名
		String user = "root";
		// MySQL配置时的密码
		String password = "";
		
		String sql = "insert into hubble.test.test values(1, 'abc', 30)";
		addRecord(driver, url, user, password, sql);

	}

	private static void addRecord(String driver, String url, String user, String password, String sql) {
		Connection con;
		try {
			// 加载驱动程序
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			if (!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			// 2.创建statement类对象，用来执行SQL语句！！
			Statement statement = con.createStatement();
			Thread.currentThread().sleep(10000);
			boolean execute = statement.execute(sql);
			System.out.println(execute);
			con.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("数据库op成功！！");
		}
	}

	public static void main1(String[] args) {
		// 声明Connection对象
		Connection con;
		// 驱动程序名
		String driver = "com.beagledata.hubble.jdbc.HubbleDriver";
		// URL指向要访问的数据库名mydata
		String url = "jdbc:hubble://192.168.118.101:30008/hubble";
		// MySQL配置时的用户名
		String user = "root";
		// MySQL配置时的密码
		String password = "";
	}

}