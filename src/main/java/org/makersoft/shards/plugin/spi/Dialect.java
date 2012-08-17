/*
 * @(#)Dialect.java 2012-8-17 下午2:35:15
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.plugin.spi;

/**
 * 数据库分页查询方言.
 * 
 * @version 2012-8-17 下午2:35:15
 * @author Feng Kuok
 */
public interface Dialect {

	/**
	 * @return 是否支持结果集限定最大条数
	 */
	boolean supportLimit();

	/**
	 * @return 是否支持结果集行数偏移及限定条数
	 */
	boolean supportOffsetLimit();

	/**
	 * 分页查询语句
	 * 
	 * @param sql
	 *            原生语句
	 * @param offset
	 *            偏移量
	 * @param limit
	 *            返回行数
	 * @return 分页语句
	 */
	String getLimitString(String sql, int offset, int limit);
}
