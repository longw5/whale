package org.whale.hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

public class HdfsTest {
	
	public static void main(String[] args) {
		
		
		
	}
	
	
	/**
     * 根据配置文件获取HDFS操作对象
     * 有两种方法：
     *  1.使用conf直接从本地获取配置文件创建HDFS对象
     *  2.多用于本地没有hadoop系统，但是可以远程访问。使用给定的URI和用户名，访问远程的配置文件，然后创建HDFS对象。
     * @return FileSystem
     */
    public FileSystem getHadoopFileSystem() {


        FileSystem fs = null;
        Configuration conf = null;

        // 方法一，本地有配置文件，直接获取配置文件（core-site.xml，hdfs-site.xml）
        // 根据配置文件创建HDFS对象
        // 此时必须指定hdsf的访问路径。
        conf = new Configuration();
        // 文件系统为必须设置的内容。其他配置参数可以自行设置，且优先级最高
        conf.set("fs.defaultFS", "hdfs://node1:9000");

        try {
            // 根据配置文件创建HDFS对象
            fs = FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 方法二：本地没有hadoop系统，但是可以远程访问。根据给定的URI和用户名，访问hdfs的配置参数
        // 此时的conf不需任何设置，只需读取远程的配置文件即可。
        /*conf = new Configuration();
        // Hadoop的用户名
        String hdfsUserName = "huabingood";

        URI hdfsUri = null;
        try {
            // HDFS的访问路径
            hdfsUri = new URI("hdfs://huabingood01:9000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            logger.error(e);
        }

        try {
            // 根据远程的NN节点，获取配置信息，创建HDFS对象
            fs = FileSystem.get(hdfsUri,conf,hdfsUserName);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(e);
        }*/

        // 方法三，反正我们没有搞懂。
        /*conf  = new Configuration();
        conf.addResource("/opt/huabingood/pseudoDistributeHadoop/hadoop-2.6.0-cdh5.10.0/etc/hadoop/core-site.xml");
        conf.addResource("/opt/huabingood/pseudoDistributeHadoop/hadoop-2.6.0-cdh5.10.0/etc/hadoop/hdfs-site.xml");

        try {
            fs = FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }*/

        return fs;
    }
}

