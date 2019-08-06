package org.whale.listener1;

public class Person {

	private String id;
	private String name;
	private int age;

	private ListenerA listenera;
	private ListenerB listenerb;

	public Person() {
		super();
	}

	public Person(String id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void registerListener(ListenerA listener) {
		this.listenera = listener;
	}
	
	public void registerListener(ListenerB listener) {
		this.listenerb = listener;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

	public void walk(Event e) {

		if (listenera != null) {

			Person p = e.getP();
			listenera.call(e);
			System.out.println(p.getId() + " 边走路， 边听歌..........");
		}
		System.out.println("走路.........");
	}

	public void run(Event e) {

		if (listenerb != null) {

			Person p = e.getP();
			listenerb.call(e);
			System.out.println(p.getName() + " 边跑步， 边招手..........");
		}
		System.out.println("跑步.........");
	}
}
