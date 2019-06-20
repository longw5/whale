package org.whale.lock;

public class TestLock {

	public static Object o1 = new Object();
	public static Object o2 = new Object();

	public static void main(String[] args) {
		
		ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
		
		
		System.out.println(threadGroup.getName());
		
		ThreadGroup parent = threadGroup.getParent();
		
		System.out.println(parent.getName());
		
		
		new Exception("Everyting").printStackTrace();
	}
	
	/**
	 * 重入锁实现可重入性原理或机制是：每一个锁关联一个线程持有者和计数器，当计数器为 0 时表示该锁没有被任何线程持有，
	 * 那么任何线程都可能获得该锁而调用相应的方法；当某一线程请求成功后，JVM会记下锁的持有线程，并且将计数器置为 1；
	 * 此时其它线程请求该锁，则必须等待；而该持有锁的线程如果再次请求这个锁，就可以再次拿到这个锁，同时计数器会递增；
	 * 当线程退出同步代码块时，计数器会递减，如果计数器为 0，则释放该锁。
	 */
	public static void main1(String[] args) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				synchronized (o1) {
					System.out.println("111111111111111111111");

					try {
						Thread.currentThread().sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("222222222222222222222");
				}

			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {

				synchronized (o1) {
					System.out.println("33333333333333333333");
					try {
						Thread.currentThread().sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("44444444444444444444");
				}
			}
		}).start();
	}
}
