package org.whale.listener1;

public class ListenerA implements Listener {

	@Override
	public void call(Event e) {
		
		Person p = e.getP();
		System.out.println("通知 " + p.getId());
	}

}
