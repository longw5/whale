package org.whale.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class TestFileReader {

	public static void main(String[] args) throws Exception {

		File file4 = new File("D:\\filetest\\111.txt");
		RandomAccessFile file = new RandomAccessFile(file4, "rw");

		byte[] b = new byte[1024];
		
		int read = file.read(b);
		
		System.out.println(read);
		System.out.println(new String(b));
	}

}
