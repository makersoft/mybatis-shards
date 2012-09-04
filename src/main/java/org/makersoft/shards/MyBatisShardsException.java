/*
 * @(#)MybatisShardsException.java 2012-8-17 下午3:02:33
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards;

/**
 * Mybatis Shards自定义异常.
 * 
 * @version 2012-8-17 下午3:02:33
 * @author Feng Kuok
 */
public class MyBatisShardsException extends RuntimeException {

	private static final long serialVersionUID = 7397841257210139784L;

	public MyBatisShardsException() {
		super();
	}

	public MyBatisShardsException(String message) {
		super(message);
	}

	public MyBatisShardsException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyBatisShardsException(Throwable cause) {
		super(cause);
	}

}
