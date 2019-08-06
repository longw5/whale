package org.whale.listener1;

public class Test {

	public static void main(String[] args) {
		
		Person p = new Person("123", "tom", 25);
		
		p.registerListener(new ListenerA());
		p.registerListener(new ListenerB());
		
		Event e = new Event(p);
		
		p.walk(e);
		p.run(e);
	}
	
}
