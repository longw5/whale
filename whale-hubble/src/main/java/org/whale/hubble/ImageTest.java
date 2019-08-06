package org.whale.hubble;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.imageio.ImageIO;

public class ImageTest {

  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    String fileName = "D://code//test.jpg";
    Class.forName("com.beagledata.hubble.jdbc.HubbleDriver");
    java.util.Properties info = new java.util.Properties();
    info.put("user", "hadoop");
    String url = "jdbc:hubble://192.168.1.215:30008/hubble/test";
    Connection connection = DriverManager.getConnection(url, info);

    String sql = "select * from test_image1";
    Statement statement;
    try{
      statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(sql);
      while (rs.next()){
        saveImage(rs.getBytes(2));
      }
      rs.close();
      statement.close();
    }catch (SQLException e){
      e.printStackTrace();
    }finally{
      connection.close();
    }
    saveImage(getImageString(fileName));
  }

  private static byte[] getImageBinary(String fileName) {
    File f = new File(fileName);
    BufferedImage bi;
    try {
      bi = ImageIO.read(f);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(bi, "jpg", baos);
      byte[] bytes = baos.toByteArray();
      return bytes;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }


  /*
   * 图片转换为二进制
   *
   * @param fileName
   *            本地图片路径
   * @return
   *       图片二进制流
   * */
  private static String getImageString(String fileName) {
    File f = new File(fileName);
    BufferedImage bi;
    try {
      bi = ImageIO.read(f);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(bi, "jpg", baos);
      byte[] bytes = baos.toByteArray();
      return Base64.getEncoder().encodeToString(bytes);
      // return encoder.encodeBuffer(bytes).trim();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 将二进制转换为图片
   * @param bytes 图片二进制流
   */
  public static void saveImage(byte[] bytes) {
    try {
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
      BufferedImage bi1 = ImageIO.read(bais);
      //可以是jpg, png, gif格式
      File w2 = new File("D://code//22.jpg");
      //不管输出什么格式图片，此处不需改动
      ImageIO.write(bi1, "jpg", w2); 
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 将二进制转换为图片
   * @param base64String 图片二进制流
   */
  public static void saveImage(String base64String) {
    try {
      byte[] bytes1 = Base64.getDecoder().decode(base64String);
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
      BufferedImage bi1 = ImageIO.read(bais);
      //可以是jpg,png,gif格式
      File w2 = new File("D://code//22.jpg");
      //不管输出什么格式图片，此处不需改动
      ImageIO.write(bi1, "jpg", w2);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
