/*
 * @(#)ExitOperationsSelectOneCollector.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.exit.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.makersoft.shards.select.SelectFactory;
import org.makersoft.shards.strategy.exit.ExitOperationsCollector;
import org.makersoft.shards.strategy.merge.ShardMergeStrategy;

/**
 * 
 */
public class ExitOperationsSelectOneCollector implements ExitOperationsCollector {

	private final SelectFactory selectFactory;
	private final String statement;
	private final ShardMergeStrategy shardMergeStrategy;
	
	public ExitOperationsSelectOneCollector(SelectFactory selectFactory, ShardMergeStrategy shardMergeStrategy){
		this.selectFactory = selectFactory;
		this.statement = selectFactory.getStatement();
		this.shardMergeStrategy = shardMergeStrategy;
	}
	
	@Override
	public List<Object> apply(List<Object> result) {
		if(statement.endsWith("count")){
			return new RowCountExitOperation().apply(result);
		}else if(statement.endsWith("sum")){
			return new AggregateExitOperation("sum").apply(result);
		}else if(statement.endsWith("min")){
			return new AggregateExitOperation("min").apply(result);
		}else if(statement.endsWith("max")){
			return new AggregateExitOperation("max").apply(result);
		}else if(statement.endsWith("avg")){
			return new AvgResultsExitOperation().apply(result);
		}else if(shardMergeStrategy != null){
			return shardMergeStrategy.merge(statement, selectFactory.getParameter(), selectFactory.getRowBounds(), result);
		}

		return result;
	}

	@Override
	public void setSqlSessionFactory(SqlSessionFactory sessionFactory) {

	}

}
