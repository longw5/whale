package org.whale.test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAuto {

	public static void main(String[] args) {
		
		//原子自增
		AtomicInteger atomicInteger = new AtomicInteger(10);
		int incrementAndGet = atomicInteger.incrementAndGet();
		System.out.println(incrementAndGet);
		
		
		atomicInteger.compareAndSet(11, 15);
		
		System.out.println(atomicInteger);
	
		atomicInteger.addAndGet(10);
		
		System.out.println(atomicInteger);
		
	
	
	}
	
}
