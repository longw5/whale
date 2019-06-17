package org.whale.test;

public class TestSync {

	static int a = 100;

	public static void main(String[] args) {

		Object lock1 = new Object();
		Object lock2 = new Object();

		new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (lock1) {
					try {
						Thread.currentThread().sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("11111111111111");
					new Thread(new Runnable() {
						@Override
						public void run() {
							synchronized (lock2) {
								System.out.println("2222222222222222");
							}
						}
					}).start();

					new Thread(new Runnable() {
						@Override
						public void run() {
							synchronized (lock2) {
								System.out.println("333333333333333333");
							}
						}
					}).start();
				}
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (lock2) {
					try {
						Thread.currentThread().sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("11111111111111");
					new Thread(new Runnable() {
						@Override
						public void run() {
							synchronized (lock1) {
								System.out.println("2222222222222222");
							}
						}
					}).start();

					new Thread(new Runnable() {
						@Override
						public void run() {
							synchronized (lock1) {
								System.out.println("333333333333333333");
							}
						}
					}).start();
				}
			}
		}).start();
	}

	public static void main3(String[] args) {

		method2();
		method1();
		if (a == 2) {
			System.out.println(a);
		}
	}

	public static synchronized void method1() {
		a = 2;
		System.out.println("method1..................");
	}

	@SuppressWarnings("static-access")
	public static synchronized void method2() {
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("method2..................");
	}

	public static void main1(String[] args) {

		new Thread(new Runnable() {

			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try {

					if (a > 50) {
						Thread.currentThread().sleep(5000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				a -= 50;
				try {
					Thread.currentThread().sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("1111111111 : " + a);
			}
		}).start();

		new Thread(new Runnable() {

			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try {

					if (a > 50) {
						Thread.currentThread().sleep(5000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				a -= 50;

				try {
					Thread.currentThread().sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("2222222222 : " + a);
			}
		}).start();

		new Thread(new Runnable() {

			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try {

					if (a > 50) {
						Thread.currentThread().sleep(5000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				a -= 50;
				try {
					Thread.currentThread().sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("3333333333 : " + a);
			}
		}).start();
	}
}
