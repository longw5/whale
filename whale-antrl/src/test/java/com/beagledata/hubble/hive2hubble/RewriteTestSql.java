package com.beagledata.hubble.hive2hubble;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.beagledata.hubble.util.SqlTrimBlankUtil;

public class RewriteTestSql {

    private String convertToHubble(String sql) throws IOException {
        return ConvertHiveSQLWithRewrite.getHplsqlListenerFromSQL(sql);
    }
    
    /**
     * Insert into db.tabl select * from db.tbl;
     * 带子查询,带库名的,where条件的
     * @author wulong
     * @throws IOException
     */
    @Test
    public void testUseInsert_F() throws IOException {
		
    	String hivSql = "insert into    table     db1.test    select * from db2.test where a=1";
    	String hubbleSql = "insert into hive.db1.test select * from hive.db2.test where a=1";
    	String convertToHubble = convertToHubble(hivSql.toUpperCase());
    	System.out.println(convertToHubble);
    	System.out.println(hubbleSql.toUpperCase());
    	//正则匹配2个以上空格，转成一个空格
    	Assert.assertEquals(convertToHubble, hubbleSql.toUpperCase());
	}
    
    /**
     * Insert into db.tabl select * from db.tbl;
     * 带子查询,带库名的
     * @author wulong
     * @throws IOException
     */
    @Test
    public void testUseInsert_E_3() throws IOException {
		
    	String hivSql = "insert into test select * from db2.test";
    	String hubbleSql = "insert into test select * from hive.db2.test";
    	String convertToHubble = convertToHubble(hivSql.toUpperCase());
    	System.out.println(convertToHubble);
    	System.out.println(hubbleSql.toUpperCase());
    	//正则匹配2个以上空格，转成一个空格
    	Assert.assertEquals(convertToHubble, hubbleSql.toUpperCase());
	}
    
    /**
     * Insert into db.tabl select * from db.tbl;
     * 带子查询,带库名的
     * @author wulong
     * @throws IOException
     */
    @Test
    public void testUseInsert_E_2() throws IOException {
		
    	String hivSql = "insert into db1.test select * from test";
    	String hubbleSql = "insert into hive.db1.test select * from test";
    	String convertToHubble = convertToHubble(hivSql.toUpperCase());
    	System.out.println(convertToHubble);
    	System.out.println(hubbleSql.toUpperCase());
    	//正则匹配2个以上空格，转成一个空格
    	Assert.assertEquals(convertToHubble, hubbleSql.toUpperCase());
	}
    
    /**
     * Insert into db.tabl select * from db.tbl;
     * 带子查询,带库名的
     * @author wulong
     * @throws IOException
     */
    @Test
    public void testUseInsert_E_1() throws IOException {
		
    	String hivSql = "insert into db1.test select * from db2.test";
    	String hubbleSql = "insert into hive.db1.test select * from hive.db2.test";
    	String convertToHubble = convertToHubble(hivSql.toUpperCase());
    	System.out.println(convertToHubble);
    	System.out.println(hubbleSql.toUpperCase());
    	//正则匹配2个以上空格，转成一个空格
    	Assert.assertEquals(convertToHubble, hubbleSql.toUpperCase());
	}
    
    /**
     * Insert into tabl select * from tbl;
     * 基本的
     * @author wulong
     * @throws IOException
     */
    @Test
    public void testUseInsert_D() throws IOException {
		
    	String hivSql = "insert into test select * from test";
    	String hubbleSql = "insert into test select * from test";
    	String convertToHubble = convertToHubble(hivSql.toUpperCase());
    	System.out.println(convertToHubble);
    	System.out.println(hubbleSql.toUpperCase());
    	//正则匹配2个以上空格，转成一个空格
    	Assert.assertEquals(convertToHubble, hubbleSql.toUpperCase());
    	
	}
    
    
    /**
     * Insert into tabl values();
     * 不带库名,一行
     * @author wulong
     * @throws IOException
     */
    @Test
    public void testUseInsert_C() throws IOException {
		
    	String hivSql = "INsErT INtO TABLE tEsT vALUES(1,'AAAA')";
    	String hubbleSql = "INSERT INTO TEST VALUES(1,'AAAA')";
    	String convertToHubble = convertToHubble(hivSql.toUpperCase());
    	System.out.println(convertToHubble);
    	System.out.println(hubbleSql.toUpperCase());
    	//正则匹配2个以上空格，转成一个空格
    	Assert.assertEquals(convertToHubble, hubbleSql.toUpperCase());
    	
	}
    
    /**
     * Insert into tabl values();
     * 带库名，多行
     * @author wulong
     * @throws IOException
     */
    @Test
    public void testUseInsert_B_2() throws IOException {
		
    	String hivSql = "INSERT INTO TABLE TEST.students\n" + 
    			"  VALUES ('fred flintstone', 35, 1.28), ('barney rubble', 32, 2.32)";
    	String hubbleSql = "INSERT INTO HIVE.TEST.students\n" + 
    			"  VALUES ('fred flintstone', 35, 1.28), ('barney rubble', 32, 2.32)";
    	String convertToHubble = convertToHubble(hivSql.toUpperCase());
    	System.out.println(convertToHubble);
    	System.out.println(hubbleSql.toUpperCase());
    	//正则匹配2个以上空格，转成一个空格
    	Assert.assertEquals(convertToHubble, hubbleSql.toUpperCase());
    	
	}
    
    /**
     * Insert into tabl values();
     * 带库名，一行

     * @throws IOException
     */                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    @Test
    public void testUseInsert_B_1() throws IOException {
		
    	String hivSql = "INsErT INtO TABLE TEST.tEsT vALUES(1,'AAAA')";
    	String hubbleSql = "INSERT INTO HIVE.TEST.TEST VALUES(1,'AAAA')";
    	String convertToHubble = convertToHubble(hivSql.toUpperCase());
    	System.out.println(convertToHubble);
    	System.out.println(hubbleSql.toUpperCase());
    	//正则匹配2个以上空格，转成一个空格
    	Assert.assertEquals(convertToHubble, hubbleSql.toUpperCase());
    	
	}
    
    /**
     * Insert into tabl() values();
     * 带参数
     * Hive语法不支持
     * @author wulong
     * @throws IOException
     */
    @Test
    public void testUseInsert_A_error() throws IOException {
		
    	String hivSql = "INsErT INtO TABLE TEST.tEsT(id int, name varchar) vALUES(1,'AAAA')";
    	String hubbleSql = "INSERT INTO HIVE.TEST.TEST(id int, name varchar) VALUES(1,'AAAA')";
    	String convertToHubble = convertToHubble(hivSql.toUpperCase());
    	System.out.println(convertToHubble);
    	System.out.println(hubbleSql.toUpperCase());
    	//正则匹配2个以上空格，转成一个空格
    	Assert.assertEquals(convertToHubble, hubbleSql.toUpperCase());
    	
    	//需要指定全部的列，没有值的列补null
    	String rehivSql = "INsErT INtO TABLE TEST.tEsT vALUES(1, 'AAAA', null, null, null)";

	}
    
    /**
     * Use sql grammar test
     * @author wulong
     * @throws IOException
     */
    @Test
    public void testUseDatabase() throws IOException {
		
    	String hivSql = "usE tesT1";
    	String hubbleSql = "use hive.test1";
    	String convertToHubble = convertToHubble(hivSql.toUpperCase());
    	System.out.println(convertToHubble);
    	System.out.println(hubbleSql.toUpperCase());
    	Assert.assertEquals(convertToHubble, hubbleSql.toUpperCase());
	}
    
    /**
     * Create table grammar test
     * @author wulong
     * @throws IOException
     */
    @Test
    public void testCreateTempTable_A() throws IOException {
    	
    	//只转换 表名称 前加 库名称 的
    	String hivSql_1 = "Create temporary table db1.test as select * from t1";
    	String hubbleSql = "CREATE temporary TABLE HIVE.TEMP.TEST as select * from t1";
    	String convertToHubble = convertToHubble(hivSql_1.toUpperCase());
    	System.out.println(convertToHubble);
    	System.out.println(hubbleSql.toUpperCase());
    	Assert.assertEquals(SqlTrimBlankUtil.sqlTrimBlank(convertToHubble), SqlTrimBlankUtil.sqlTrimBlank(hubbleSql.toUpperCase()));
    }
    
    /**
     * Create table grammar test
     * @author wulong
     * @throws IOException
     */
    @Test
    public void testCreateTable1() throws IOException {
    	
    	//只转换 表名称 前加 库名称 的
    	String hivSql_1 = "Create     table    db1.test(id int, name    varchar, age int,    note varchar)";
    	String hubbleSql = "CREATE TABLE HIVE.DB1.TEST(ID INT, NAME VARCHAR, AGE INT, NOTE VARCHAR)";
    	String convertToHubble = convertToHubble(hivSql_1.toUpperCase());
    	System.out.println(convertToHubble);
    	System.out.println(hubbleSql);
    	Assert.assertEquals(convertToHubble, hubbleSql.toUpperCase());
    }
    
    /**
     * create table...format  test
     * @author wulong
     * @throws IOException
     */
    @Test
    public void testCreateTable2() throws IOException {
    	
    	String hivSql = "CREATE TABLE TEST.TEST_ORC\n" + 
    			"(\n" + 
    			"   ROWID INTEGER,\n" + 
    			"   NAME VARCHAR,\n" + 
    			"   AGE INTEGER,\n" + 
    			"   GENDER VARCHAR\n" + 
    			")\n" + 
    			"ROW FORMAT DELIMITED FIELDS TERMINATED BY ','\n" + 
    			"STORED AS ORC";
    	
    	String hubbleSql = "CREATE TABLE HIVE.TEST.TEST_ORC (\n" + 
    			"   ROWID INTEGER,\n" + 
    			"   NAME VARCHAR,\n" + 
    			"   AGE INTEGER,\n" + 
    			"   GENDER VARCHAR\n" + 
    			")\n" + 
    			"WITH (\n" + 
    			"   FORMAT = 'ORC'\n" + 
    			");";
    	
    	String convertToHubble = convertToHubble(hivSql.toUpperCase());
    	
    	System.out.println(convertToHubble);
    	System.out.println("===============================================");
    	System.out.println(hubbleSql);
    	
    	Assert.assertEquals(convertToHubble, hubbleSql.toUpperCase());
    	
    }
}
