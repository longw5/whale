package org.whale.math;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class TestPriorityQueue {

	//priorty queue的 比较器排序
	public static void main(String[] args) {
		
		int[] arr = {9,6,3,8,11,55};
		
		List asList = Arrays.asList(arr);
		Collections.sort(asList);
		
		Iterator iterator = asList.iterator();
		
		while (iterator.hasNext()) {
			Integer object = (Integer) iterator.next();
			System.out.println(object);
		}
		
		PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(arr.length, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				
				System.out.println(o1.intValue()+"::"+o2.intValue());
				return o1.intValue()-o2.intValue();
			}
		});
		
		for (int i = 0; i < arr.length; i++) {
			queue.add(arr[i]);
		}
		System.out.println(queue);
	}
	
}
