package org.makersoft.shards.unit.proxy;

public class HelloWorldImpl implements HelloWorld {
	
	public HelloWorldImpl(){
		System.out.println("HelloWorldImpl");
	}
	public void sayHelloWorld() {
		System.out.println("Hello World!");
	}
}
