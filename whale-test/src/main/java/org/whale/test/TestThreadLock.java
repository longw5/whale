package org.whale.test;

public class TestThreadLock {

	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				printA();
			}
		}).start();
		
		new Thread(new Runnable() {
					
			@Override
			public void run() {
				printB();
			}
			}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				printC();
			}
		}).start();
		
	}
	
	public synchronized static void printA() {
		System.out.println("AAAAAAAAAAAAAAAAAAAAA");
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static void printB() {
		System.out.println("BBBBBBBBBBBBBBBBBBBBB");
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static void printC() {
		System.out.println("CCCCCCCCCCCCCCCCCCCCC");
		try {
			Thread.currentThread().sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
