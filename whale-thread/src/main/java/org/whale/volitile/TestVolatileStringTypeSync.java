package org.whale.volitile;

/**
 * volatile保证可读性，不能保证原子性，线程并发问题
 * 
 * @author wulong
 */
public class TestVolatileStringTypeSync {

	volatile static String s1 = "abc";

	public static Object lock = new Object();

	/**
	 * volatile使cpu的L1、L2缓存失效，但是线程读取后的缓存不会失效
	 * 不能使用String类型的充当锁，因为String类型进行操作拼接时，地址会改变
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				synchronized (lock) {

					s1 = s1 + "def";
					System.out.println(Thread.currentThread().getName() + " : " + s1);

					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					s1 = s1 + "qwe";
					System.out.println(Thread.currentThread().getName() + " : " + s1);
				}

			}
		}, "Thread 1").start();

		new Thread(new Runnable() {

			@Override
			public void run() {

				synchronized (lock) {
					s1 = s1 + "def";
					System.out.println(Thread.currentThread().getName() + " : " + s1);

					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					s1 = s1 + "qwe";
					System.out.println(Thread.currentThread().getName() + " : " + s1);
				}
			}
		}, "Thread 2").start();

	}

}
