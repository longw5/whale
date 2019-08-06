package org.whale.listener2;

public class Test {

	public static void main(String[] args) {
		
		Person p = new Person("123", "tom", 25);
		
		p.registerListener(new Listener() {
			
			@Override
			public void callBack(Event e) {
				System.out.println("call.........."+e.getP().getId()+"............");
			}
		});
		
		Event e = new Event(p);
		
		p.walk(e);
		p.run(e);
	}
}
