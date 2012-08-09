/*
 * @(#)ShardSelectionStrategy.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.selection;

import org.makersoft.shards.ShardId;

/**
 * 创建新对象时的，分区筛选策略
 */
public interface ShardSelectionStrategy {

	/**
	 * Determine the specific shard on which this object should reside
	 * 
	 * @param obj
	 *            the new object for which we are selecting a shard
	 * @return the id of the shard on which this object should live
	 */
	ShardId selectShardIdForNewObject(String statement, Object obj);
	
//	List<ShardId> selectShardIdByStatement(String statement, Object parameter);
}
