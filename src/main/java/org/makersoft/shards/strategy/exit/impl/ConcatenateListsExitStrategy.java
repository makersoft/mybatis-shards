/*
 * @(#)ConcatenateListsExitStrategy.java    Jul 16, 2012  10:09:43 AM
 * Copyright (c) 2012 360buy All rights reserved.
 * 
 * wos:工单项目
 */
package org.makersoft.shards.strategy.exit.impl;

import java.util.List;

import org.makersoft.shards.Shard;
import org.makersoft.shards.strategy.exit.ExitOperationsCollector;
import org.makersoft.shards.strategy.exit.ExitStrategy;
import org.makersoft.shards.utils.Lists;

/**
 * Threadsafe ExistStrategy that concatenates all the lists that are added.
 */
public class ConcatenateListsExitStrategy implements ExitStrategy<List<Object>> {

	private final List<Object> result = Lists.newArrayList();

	public synchronized boolean addResult(List<Object> oneResult, Shard shard) {
		result.addAll(oneResult);
		return false;
	}

	@Override
	public List<Object> compileResults(ExitOperationsCollector exitOperationsCollector) {
		return exitOperationsCollector.apply(result);
	}
	
}
