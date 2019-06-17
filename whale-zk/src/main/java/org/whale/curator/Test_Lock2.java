package org.whale.curator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Test_Lock2 {

	static String lock_path = "/curator_path2";

	static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
			.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

	public static void main(String[] args) {

		client.start();
		final InterProcessMutex lock = new InterProcessMutex(client, lock_path);
		CountDownLatch downLatch = new CountDownLatch(1);

		for (int i = 0; i < 30; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						downLatch.await();
						lock.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
					String order_no = sdf.format(new Date());
					System.err.println("生成的订单号 : " + order_no);
					try {
						lock.release();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		downLatch.countDown();
	}

}
