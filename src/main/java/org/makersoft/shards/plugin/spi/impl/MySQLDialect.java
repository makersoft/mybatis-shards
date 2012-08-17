/*
 * @(#)MySqlDialect.java 2012-8-17 下午2:54:51
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.plugin.spi.impl;

import org.makersoft.shards.plugin.spi.Dialect;

/**
 * MySQL数据库分页方言.
 * 
 * @version 2012-8-17 下午2:54:51
 * @author Feng Kuok
 */
public class MySQLDialect implements Dialect {

	@Override
	public boolean supportLimit() {
		return true;
	}

	@Override
	public boolean supportOffsetLimit() {
		return true;
	}

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		if (offset > 0) {
			return sql + " limit " + offset + ", " + limit;
		} else {
			return sql + " limit " + limit;
		}
	}

}
