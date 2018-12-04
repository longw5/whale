package org.whale.test;

/**
 * 栈溢出
 * 
 * @author wulong
 *
 */
public class TestStackError {

	public static void main(String[] args) {
		new TestStackError().test();
	}

	public void test() {
		test();
	}

}
