package org.makersoft.shards.unit.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		InvocationHandler handler = new HelloWorldHandler();

		HelloWorld proxy = (HelloWorld) Proxy.newProxyInstance(HelloWorldImpl.class.getClassLoader(), new Class[] { HelloWorld.class }, handler);
		System.out.println(proxy);
		proxy.sayHelloWorld();

	}

}
