/*
 * @(#)ShardResolutionStrategy.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.resolution;

import java.util.List;

import org.makersoft.shards.ShardId;

/**
 * 分区抉择策略
 */
public interface ShardResolutionStrategy {

	/**
	 * 决定此查询在哪些分区上执行
	 *
	 * @param shardResolutionStrategyData information we can use to select shards
	 * @return the ids of the shards on which the object described by the ShardSelectionStrategyData might reside
	 */
	List<ShardId> selectShardIdsFromShardResolutionStrategyData(ShardResolutionStrategyData shardResolutionStrategyData);
}
