/*
 * @(#)ExitOperationsSelectCollector.java 2012-9-7 下午1:38:06
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.exit.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.makersoft.shards.select.SelectFactory;
import org.makersoft.shards.strategy.exit.ExitOperationsCollector;
import org.makersoft.shards.strategy.reduce.ShardReduceStrategy;

/**
 * Class description goes here.
 * 
 * @version 2012-9-7 下午1:38:06
 * @author Feng Kuok
 */
public class ExitOperationsSelectCollector implements ExitOperationsCollector {
	
	private final SelectFactory selectFactory;
	
	private final ShardReduceStrategy shardReduceStrategy;
	
	private final RowBounds rowBounds;
	
	
	public ExitOperationsSelectCollector(SelectFactory selectFactory, ShardReduceStrategy shardReduceStrategy){
		this.selectFactory = selectFactory;
		this.shardReduceStrategy = shardReduceStrategy;
		this.rowBounds = selectFactory.getRowBounds();
	}

	@Override
	public List<Object> apply(List<Object> result) {
		
		result = shardReduceStrategy.reduce(selectFactory.getStatement(), selectFactory.getParameter(), rowBounds, result);
		
		if (rowBounds != null && rowBounds != RowBounds.DEFAULT) {
			result = new RowBoundsExitOperation(rowBounds).apply(result);
		}
		
		return result;
	}

	@Override
	public void setSqlSessionFactory(SqlSessionFactory sessionFactory) {
		throw new UnsupportedOperationException();
	}

}
