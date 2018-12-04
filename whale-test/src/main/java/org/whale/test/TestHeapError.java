package org.whale.test;

import java.util.ArrayList;

/**
 * 堆溢出
 * @author wulong
 *
 */
public class TestHeapError {
	public static void main(String[] args) {

		ArrayList list = new ArrayList();
		while (true) {
			list.add(new TestHeapError());
		}
	}
}
