/*
 * @(#)ShardStrategy.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy;

import org.makersoft.shards.strategy.access.ShardAccessStrategy;
import org.makersoft.shards.strategy.reduce.ShardReduceStrategy;
import org.makersoft.shards.strategy.resolution.ShardResolutionStrategy;
import org.makersoft.shards.strategy.selection.ShardSelectionStrategy;

/**
 * 分区策略接口.
 *
 * @author Feng Kuok
 */
public interface ShardStrategy {
	
	ShardSelectionStrategy getShardSelectionStrategy();

	ShardResolutionStrategy getShardResolutionStrategy();

	ShardAccessStrategy getShardAccessStrategy();
	
	ShardReduceStrategy getShardReduceStrategy();
	
}
