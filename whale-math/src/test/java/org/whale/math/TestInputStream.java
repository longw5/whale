package org.whale.math;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestInputStream {

	public static void main(String[] args) throws Exception {

		FileInputStream fis = new FileInputStream(new File("D://abc.txt"));

		int available = fis.available();
		
		while (available > 0) {
			
			byte[] b = new byte[1];
			
			int read = fis.read(b);
			System.out.println(read);
			System.out.println(new String(b));
			System.out.println(b[0]);
			available--;
		}
	}

	public static void main1(String[] args) throws IOException {

		FileInputStream fis = new FileInputStream(new File("D://abc.txt"));

		int available = fis.available();

		while (available > 0) {

			int read = fis.read();
			System.out.println(read);
			available--;
		}
	}

}
