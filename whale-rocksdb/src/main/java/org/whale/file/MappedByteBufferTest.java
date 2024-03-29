package org.whale.file;

import java.nio.channels.FileChannel;

public class MappedByteBufferTest {
	
	/**
	 * 修改文件中的某一部分的数据测试:将字定位置的字母改为大写
	 * 
	 * @param fName
	 *            :要修改的文件名字
	 * @param start:起始字节
	 * @param len:要修改多少个字节
	 * @return :是否修改成功
	 * @throws Exception:文件读写中可能出的错
	 * @author javaFound
	 */
	public static boolean changeFile(String fName, int start, int len) throws Exception {
		// 创建一个随机读写文件对象
		java.io.RandomAccessFile raf = new java.io.RandomAccessFile(fName, "rw");
		long totalLen = raf.length();
		System.out.println("文件总长字节是: " + totalLen);
		// 打开一个文件通道
		java.nio.channels.FileChannel channel = raf.getChannel();
		// 映射文件中的某一部分数据以读写模式到内存中
		java.nio.MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, start, len);
		// 示例修改字节
		for (int i = 0; i < len; i++) {
			byte src = buffer.get(i);
			buffer.put(i, (byte) (src - 31));// 修改Buffer中映射的字节的值
			System.out.println("被改为大写的原始字节是:" + src);
		}
		buffer.force();// 强制输出,在buffer中的改动生效到文件
		buffer.clear();
		channel.close();
		raf.close();
		return true;
	}

	// 测试主方法
	public static void main(String[] args) throws Exception {
		changeFile("BigFileRW.java", 3, 5);
		System.out.println(" change OK... ");
	}
}
