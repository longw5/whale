package org.whale.listener1;

public class Event {
	
	public Person p;

	public Event() {
		super();
	}

	public Event(Person p) {
		super();
		this.p = p;
	}

	public Person getP() {
		return p;
	}

	public void setP(Person p) {
		this.p = p;
	}

	@Override
	public String toString() {
		return "Event [p=" + p + "]";
	}
}
