package org.whale.curator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

public class Test_Lock {

	public static void main(String[] args) {
		
		CountDownLatch downLatch = new CountDownLatch(1);
		
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					
					try {
						downLatch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
					String order_no = sdf.format(new Date());
					System.err.println("生成的订单号 : " + order_no);
				}
			}).start();
		}
		downLatch.countDown();
	}
	
}
