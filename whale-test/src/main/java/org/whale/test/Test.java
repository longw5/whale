package org.whale.test;

public class Test {

	public static void main(String[] args) {
		
		
		
	}
	
	public static void main2(String[] args) {
		
		int vcores = Runtime.getRuntime().availableProcessors();
		System.out.println(vcores);
	} 
	
	
	public static void main1(String[] args) {
		
		String icode = "340122820307095";
		
		StringBuilder replace = new StringBuilder(icode).replace(3, 12, "************");
		
		System.out.println(icode.length());
		System.out.println(replace.length());
		System.out.println(replace);
		System.out.println("340************956".length());
	}
	
}
