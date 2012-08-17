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
public class MybatisShardsException extends RuntimeException {

	private static final long serialVersionUID = 7397841257210139784L;

	public MybatisShardsException() {
		super();
	}

	public MybatisShardsException(String message) {
		super(message);
	}

	public MybatisShardsException(String message, Throwable cause) {
		super(message, cause);
	}

	public MybatisShardsException(Throwable cause) {
		super(cause);
	}

}
