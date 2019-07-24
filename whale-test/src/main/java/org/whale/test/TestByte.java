package org.whale.test;

import java.nio.charset.Charset;

public class TestByte {

	private static final byte FLAG_STRING = 2;
	
	
	public static void main(String[] args) {
		
		
		long l1 = (1563257814916L-1497950831575L) << 22;
		
		System.out.println(l1);  //273917341455089664
		
		long l2 = 2 << 17;
		
		System.out.println(l2);  //262144
		
		long l3 = 2 << 12;
		
		System.out.println(l3);  //8192
		
		long l4 = 0;
		
		System.out.println(l1 | 262144 | 8192 | l2);
	}
	
	
	public static void main1(String[] args) {
		
			//Hubble数据库数据库编码
			byte[] bytes = "TEST7".getBytes(Charset.forName("UTF-8"));
			
			byte[] re = new byte[bytes.length + 1];
			
			re[0] = FLAG_STRING;
			
			System.arraycopy(bytes, 0, re, 1, bytes.length);
			
			String symbols = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			
			int i = (int)(7607336548960256L%36);
			
			System.out.println(i);
			
		
	}
	
	/*public byte[] tobytes() throws IOException {
		String value = StringUtils.join(ids, SPLIT);
		byte[] vBytes = value.getBytes(Charset.forName("UTF-8"));
		byte[] re = new byte[vBytes.length + 1];
		re[0] = getFlag();
		System.arraycopy(vBytes, 0, re, 1, vBytes.length);
		return re;
	}*/
	
}
