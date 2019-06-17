package org.whale.hubble;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class main {

	public static void main11(String[] args) {

		// 声明Connection对象
		Connection con = null;
		// 驱动程序名
		String driver = "com.beagledata.hubble.jdbc.HubbleDriver";
		// URL指向要访问的数据库名mydata
		String url = "jdbc:hubble://192.168.1.215:30008/hubble";
		// MySQL配置时的用户名
		String user = "root";
		// MySQL配置时的密码
		String password = "";

		String sql1 = "insert into hubble.test.test values('123', '30')";
		String sql2 = "insert into hubble.test.test values('123', '30')";
		
		try {
			// 加载驱动程序
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			if (!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			// 2.创建statement类对象，用来执行SQL语句！！
//			con.setAutoCommit(false);
			Statement statement = con.createStatement();
			
			int i = 1;
			
			while(true) {
				
				if(i > 10000)
					break;
				if(i % 77 == 0) {
					boolean execute = statement.execute(sql2);
				}else {
					boolean execute = statement.execute(sql1);
				}
				i++;
			}
//			con.commit();
			con.close();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		}finally {
			System.out.println("数据库op成功！！");
		}

	}

	static int y = 1;

	public static void main(String[] args) {
		// 声明Connection对象
		Connection con;
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名mydata
		String url = "jdbc:mysql://192.168.1.215:3307/";
		// MySQL配置时的用户名
		String user = "hadoop";
		// MySQL配置时的密码
		String password = "";

		try {
			// 加载驱动程序
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			if (!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			// 2.创建statement类对象，用来执行SQL语句！！
			System.out.println(con.isClosed());
			Statement statement = con.createStatement();
			boolean execute = statement.execute("show catalogs");
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