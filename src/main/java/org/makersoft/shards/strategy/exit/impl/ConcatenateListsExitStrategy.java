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
import java.util.concurrent.CopyOnWriteArrayList;

import org.makersoft.shards.Shard;
import org.makersoft.shards.strategy.exit.ExitOperationsCollector;
import org.makersoft.shards.strategy.exit.ExitStrategy;

/**
 * Threadsafe ExistStrategy that concatenates all the lists that are added.
 */
public class ConcatenateListsExitStrategy implements ExitStrategy<List<Object>> {

	private final List<Object> nonNullResult = new CopyOnWriteArrayList<Object>();

	public boolean addResult(List<Object> oneResult, Shard shard) {
		if(oneResult != null && !oneResult.isEmpty()){
			nonNullResult.addAll(oneResult);
		}
		return false;
	}

	@Override
	public List<Object> compileResults(ExitOperationsCollector exitOperationsCollector) {
		return exitOperationsCollector.apply(nonNullResult);
	}
	
}
