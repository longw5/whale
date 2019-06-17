package org.whale.rw;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class TestRW {

	static String content = "memtable/写缓冲(write buffer)：在内存中存储最新更新的数据的数据结构。通常它会按顺序组织，并且会包含一个二分查找索引。参考https://rocksdb.org.cn/doc/Basic-Operations.html#memtable-and-table-factories";

	// 写入大文件 buffer写出方式 追加方式写出
	public static void main(String[] args) throws Exception {

		Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    // do something
                	long end = System.currentTimeMillis();
                    System.out.println(end);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
		
		// 100000 ssd time : 94
		// 1000000 ssd time : 832
		// 10000000 ssd time : 7451 18165 19090 7135 6549 13145 21046 6716
		// 6410 7831 9676 6136
		// 10000000 ssd time : 150 mb/s
		// 1557977838415 1557978006981=168566
		// 100000 hdd time : 85
		// 1000000 hdd time : 1217 789 731 728 701
		long start = System.currentTimeMillis();
		System.out.println(start);

		BufferedOutputStream bios = new BufferedOutputStream(
				new FileOutputStream(new File("C:\\Temp\\bigfile.txt"), true));
		for (int i = 0; i < 100000000; i++) {
			bios.write(content.getBytes());
		}
		bios.close();

//		long end = System.currentTimeMillis();

//		System.out.println("time : " + (end - start));
	}

	// 写入小文件
	public static void main2(String[] args) throws Exception {

		Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    // do something
                	long end = System.currentTimeMillis();
                    System.out.println(end);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
		
		// 100000 ssd time : 171
		// 1000000 ssd time : 2598
		// 10000000 ssd time : 
		//1557977540840 1557977696377 = 155537
		// 100000 hdd time : 283 279 263 310
		// 1000000 hdd time : 3012 3419 3254 3039
		long start = System.currentTimeMillis();
		System.out.println(start);

		BufferedOutputStream bios = new BufferedOutputStream(
				new FileOutputStream(new File("C:\\Temp\\bigfile2.txt"), true));

		StringBuilder builder = new StringBuilder();
//		long start2 = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			
			while(i%1000000 == 0) {
				builder.append(content);
				bios.write(builder.toString().getBytes());
				builder = null;
				builder = new StringBuilder();
			}
		}
//		long end2 = System.currentTimeMillis();
		bios.close();
		long end = System.currentTimeMillis();

		System.out.println("time : " + (end - start));
//		System.out.println("time2 : " + (end2 - start2));
		
	}

	// 写入大文件 buffer写出方式 追加方式写出
	public static void main3(String[] args) throws Exception {

		// 100000 ssd time : 229319
		// 1000000 ssd time : 
		// 100000 hdd time : 219582
		// 1000000 hdd time : 
		long start = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
		BufferedOutputStream bios = new BufferedOutputStream(
				new FileOutputStream(new File("C:\\Temp\\bigfile3.txt"), true));
			bios.write(content.getBytes());
			bios.close();
		}

		long end = System.currentTimeMillis();

		System.out.println("time : " + (end - start));
	}

}
