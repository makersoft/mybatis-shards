/*
 * @(#)ShardStrategyImpl.java 2012-8-1 下午10:00:00
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
import org.makersoft.shards.utils.Assert;

/**
 * @author Feng Kuok 
 */
public class ShardStrategyImpl implements ShardStrategy {

	private ShardSelectionStrategy shardSelectionStrategy;

	private ShardResolutionStrategy shardResolutionStrategy;

	private ShardAccessStrategy shardAccessStrategy;

	private ShardReduceStrategy shardReduceStrategy;

	public ShardStrategyImpl(ShardSelectionStrategy shardSelectionStrategy,
			ShardResolutionStrategy shardResolutionStrategy,
			ShardAccessStrategy shardAccessStrategy, ShardReduceStrategy shardReduceStrategy) {

		Assert.notNull(shardSelectionStrategy);
		Assert.notNull(shardResolutionStrategy);
		Assert.notNull(shardAccessStrategy);
		Assert.notNull(shardReduceStrategy);

		this.shardSelectionStrategy = shardSelectionStrategy;
		this.shardResolutionStrategy = shardResolutionStrategy;
		this.shardAccessStrategy = shardAccessStrategy;
		this.shardReduceStrategy = shardReduceStrategy;
	}

	@Override
	public ShardSelectionStrategy getShardSelectionStrategy() {
		return shardSelectionStrategy;
	}

	@Override
	public ShardResolutionStrategy getShardResolutionStrategy() {
		return shardResolutionStrategy;
	}

	@Override
	public ShardAccessStrategy getShardAccessStrategy() {
		return shardAccessStrategy;
	}

	@Override
	public ShardReduceStrategy getShardReduceStrategy() {
		return shardReduceStrategy;
	}

}
