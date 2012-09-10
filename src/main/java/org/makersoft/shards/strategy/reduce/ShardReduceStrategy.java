/*
 * @(#)ShardReduceStrategy.java 2012-9-4 下午4:57:59
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.reduce;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

/**
 * 分区结果集合并策略.
 * 
 * @version 2012-9-4 下午4:57:59
 * @author Feng Kuok
 */
public interface ShardReduceStrategy {

	/**
	 * 计算结果集
	 * @param statement	
	 * @param parameter
	 * @param rowBounds
	 * @param values noNullList 
	 * @return
	 */
	List<Object> reduce(String statement, Object parameter, RowBounds rowBounds, List<Object> values);
}
