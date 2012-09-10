/*
 * @(#)ConcatenateListsExitStrategy.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
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

	private final List<Object> nonNullResult = Lists.newArrayList();

	public synchronized boolean addResult(List<Object> oneResult, Shard shard) {
		if(nonNullResult != null){
			nonNullResult.add(oneResult);
		}
		return false;
	}

	@Override
	public List<Object> compileResults(ExitOperationsCollector exitOperationsCollector) {
		return exitOperationsCollector.apply(nonNullResult);
	}
	
}
