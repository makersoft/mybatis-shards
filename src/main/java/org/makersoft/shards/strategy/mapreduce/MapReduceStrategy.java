/*
 * @(#)MapReduceStrategy.java 2013-3-23 下午23:33:33
 *
 * Copyright (c) 2011-2013 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.mapreduce;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.makersoft.shards.ShardId;

/**
 * map reduce strategy
 */
public interface MapReduceStrategy<T>{

	void map(final Object parameter, OutputCollector<ShardId> output);
	
	void reduce(List<T> inputValues, final Object parameter, final RowBounds rowBounds, OutputCollector<T> output);
}
