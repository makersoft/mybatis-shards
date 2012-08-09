/*
 * @(#)AdHocSelectFactoryImpl.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.select.impl;

import org.apache.ibatis.session.RowBounds;
import org.makersoft.shards.select.SelectFactory;

/**
 * 
 */
public class AdHocSelectFactoryImpl implements SelectFactory {

	private final String statement;
	private final Object parameter;
	private final String mapKey;
	private final RowBounds rowBounds;

	public AdHocSelectFactoryImpl(String statement, Object parameter,
			String mapKey, RowBounds rowBounds) {
		this.statement = statement;
		this.parameter = parameter;
		this.mapKey = mapKey;
		this.rowBounds = rowBounds;

	}

	@Override
	public String getStatement() {
		return statement;
	}

	@Override
	public Object getParameter() {
		return parameter;
	}

	@Override
	public String getMapKey(){
		return mapKey;
	}
	
	@Override
	public RowBounds getRowBounds() {
		return rowBounds;
	}

}
