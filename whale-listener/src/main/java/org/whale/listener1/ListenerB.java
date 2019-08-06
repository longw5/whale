package org.whale.listener1;

public class ListenerB implements Listener {

	@Override
	public void call(Event e) {
		
		Person p = e.getP();
		System.out.println("训练 " + p.getName());
	}
}
