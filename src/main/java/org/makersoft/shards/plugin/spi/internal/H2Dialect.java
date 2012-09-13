/*
 * @(#)H2Dialect.java 2012-8-17 下午5:36:21
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.plugin.spi.internal;

import org.makersoft.shards.plugin.spi.Dialect;

/**
 * H2 数据库方言.
 * 
 * @version 2012-8-17 下午5:36:21
 * @author Feng Kuok
 */
public class H2Dialect implements Dialect{

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
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 40).append(sql);
		if(offset > 0){
			return pagingSelect.append(" limit ").append(limit).append(" offset ").append(offset).toString();
		}else {
			return pagingSelect.append(" limit ").append(limit).toString();
		}
		
	}

}
