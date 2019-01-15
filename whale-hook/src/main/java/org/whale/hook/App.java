package org.whale.hook;

/**
 * Hello world!
 *
 */
public class App {
	public static void main( String[] args )
    {
		
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    	// 虚拟机退出时关闭
    	   Runtime.getRuntime().addShutdownHook(new Thread() {
    	    @Override
    	    public void run() {
    	    	 System.out.println("jvm is close..............");
    	    }
    	  });
   		System.out.println("ccccccccccccccccccccccccccccccc");
    }
}
