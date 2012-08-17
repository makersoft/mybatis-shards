/*
 * @(#)OracleDialect.java 2012-8-17 下午6:00:14
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.plugin.spi.impl;

import org.makersoft.shards.plugin.spi.Dialect;

/**
 * Oracle (any version) 数据库方言.
 * 
 * @version 2012-8-17 下午6:00:14
 * @author Feng Kuok
 */
public class OracleDialect implements Dialect {

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
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update") ) {
			sql = sql.substring( 0, sql.length() - 11 );
			isForUpdate = true;
		}
		
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (offset > 0) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		
		pagingSelect.append(sql);
		
		if (offset > 0) {
			pagingSelect.append(" ) row_ ) where rownum_ <= ").append(offset + limit).append(" and rownum_ > ").append(offset);
		} else {
			pagingSelect.append(" ) where rownum <= " + limit);
		}

		if ( isForUpdate ) {
			pagingSelect.append( " for update" );
		}
		
		return pagingSelect.toString();
	}

}
