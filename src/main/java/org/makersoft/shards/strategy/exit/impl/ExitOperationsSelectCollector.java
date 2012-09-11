/*
 * @(#)ExitOperationsSelectCollector.java 2012-9-7 下午1:38:06
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.exit.impl;

import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.makersoft.shards.select.SelectFactory;
import org.makersoft.shards.strategy.exit.ExitOperationsCollector;
import org.makersoft.shards.strategy.reduce.ShardReduceStrategy;

/**
 * .
 * 
 * @version 2012-9-7 下午1:38:06
 * @author Feng Kuok
 */
public class ExitOperationsSelectCollector implements ExitOperationsCollector {
	
	private final String statement;
	private final Object parameter;
	private final RowBounds rowBounds;
	
	private final ShardReduceStrategy shardReduceStrategy;
	
	
	public ExitOperationsSelectCollector(SelectFactory selectFactory, ShardReduceStrategy shardReduceStrategy){
		this.statement = selectFactory.getStatement();
		this.parameter = selectFactory.getParameter();
		this.rowBounds = selectFactory.getRowBounds();
		
		this.shardReduceStrategy = shardReduceStrategy;
		
	}

	@Override
	public List<Object> apply(List<Object> values) {
		
		if(!values.isEmpty()){
			//调用reduce策略
			List<Object> results = shardReduceStrategy.reduce(statement, parameter, rowBounds, values);
			
			values = (results != null) ? results : Collections.emptyList();	//去除结果为null的情况
			
			if (rowBounds != null && rowBounds != RowBounds.DEFAULT) {
				values = new RowBoundsExitOperation(rowBounds).apply(values);
			}
			
		}
		
		return values;
	}

	@Override
	public void setSqlSessionFactory(SqlSessionFactory sessionFactory) {
		throw new UnsupportedOperationException();
	}

}
