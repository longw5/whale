package org.whale.hubble;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.UUID;

import javax.imageio.ImageIO;

public class TestInsertHubbleImg {

	public static void main(String[] args) {

		// 声明Connection对象
		Connection con = null;
		// 驱动程序名
		String driver = "com.beagledata.hubble.jdbc.HubbleDriver";
		// URL指向要访问的数据库名mydata
		String url = "jdbc:hubble://192.168.1.215:30008";
		// MySQL配置时的用户名
		String user = "hadoop";
		// MySQL配置时的密码
		String password = "";

		String sql = "select * from hubble.hubbletest3.img_test5";

		try {
			// 加载驱动程序
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			if (!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");

			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				
				String imgString = rs.getString(2);
				saveImage(imgString);
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	public static void main1(String[] args) throws ClassNotFoundException, SQLException {

		// 声明Connection对象
		Connection con = null;
		// 驱动程序名
		String driver = "com.beagledata.hubble.jdbc.HubbleDriver";
		// URL指向要访问的数据库名mydata
		String url = "jdbc:hubble://192.168.1.215:30008";
		// MySQL配置时的用户名
		String user = "hadoop";
		// MySQL配置时的密码
		String password = "";

		// busi_guid, img
		String busi_guid = UUID.randomUUID().toString();

		byte[] imageBinary = getImageBinary("D:\\img\\1.png");

		String encodeToString = Base64.getEncoder().encodeToString(imageBinary);

		String sql = "INSERT INTO hubble.hubbletest3.img_test5(busi_guid, img) values('" + busi_guid + "', '"
				+ encodeToString + "')";

		try {
			// 加载驱动程序
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			if (!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");

			con.setAutoCommit(false);
			Statement statement = con.createStatement();
			int executeLargeUpdate = statement.executeUpdate(sql);
			System.out.println(executeLargeUpdate);
			con.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static byte[] getImageBinary(String fileName) {
		File f = new File(fileName);
		BufferedImage bi;
		try {
			bi = ImageIO.read(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "png", baos);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void saveImage(String base64String) {
	    try {
	      byte[] bytes1 = Base64.getDecoder().decode(base64String);
	      ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
	      BufferedImage bi1 = ImageIO.read(bais);
	      //可以是jpg,png,gif格式
	      File w2 = new File("D://img//22.jpg");
	      //不管输出什么格式图片，此处不需改动
	      ImageIO.write(bi1, "png", w2);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
	
}
