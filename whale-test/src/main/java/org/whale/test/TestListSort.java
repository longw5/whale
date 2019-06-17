package org.whale.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class TestListSort {

	public static void main(String[] args) {
		

		Stack<Object> stack = new Stack<>();
		
		stack.add("a");
		stack.add("b");
		stack.add("c");
		stack.add("d");
		stack.add("e");
		stack.add("f");
		stack.add("g");
		stack.add("h");
		stack.add("i");
		
		System.out.println(stack);
		
		int size = stack.size();
		System.out.println(size);
		
		Object object = stack.get(0);
		System.out.println(object);
		
		stack.setSize(10);
		int size1 = stack.size();
		System.out.println(size1);
		
		System.out.println(stack);
	}
	
}
