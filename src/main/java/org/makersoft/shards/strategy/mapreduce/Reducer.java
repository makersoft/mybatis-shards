/*
 * @(#)Reducer.java 2013-3-23 下午23:33:33
 *
 * Copyright (c) 2011-2013 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.mapreduce;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

/**
 * 
 */
public interface Reducer<T> {
	void reduce(List<T> inputValues, final Object parameter, final RowBounds rowBounds, OutputCollector<T> output);
}
