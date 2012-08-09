/*
 * @(#)ExitOperationsSelectMapCollector.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.exit.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.makersoft.shards.strategy.exit.ExitOperationsCollector;

/**
 * 
 */
public class ExitOperationsSelectMapCollector implements ExitOperationsCollector {

	@Override
	public List<Object> apply(List<Object> result) {
		return null;
	}

	@Override
	public void setSqlSessionFactory(SqlSessionFactory sessionFactory) {
		
	}

}
