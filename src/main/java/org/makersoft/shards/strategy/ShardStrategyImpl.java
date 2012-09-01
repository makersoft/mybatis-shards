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
import org.makersoft.shards.strategy.merge.ShardMergeStrategy;
import org.makersoft.shards.strategy.resolution.ShardResolutionStrategy;
import org.makersoft.shards.strategy.selection.ShardSelectionStrategy;
import org.makersoft.shards.utils.Assert;

/**
 * Class description goes here.
 * 
 * 
 */
public class ShardStrategyImpl implements ShardStrategy {

	private ShardSelectionStrategy shardSelectionStrategy;

	private ShardResolutionStrategy shardResolutionStrategy;

	private ShardAccessStrategy shardAccessStrategy;
	
	private ShardMergeStrategy shardMergeStrategy;

	public ShardStrategyImpl(ShardSelectionStrategy shardSelectionStrategy,
			ShardResolutionStrategy shardResolutionStrategy,
			ShardAccessStrategy shardAccessStrategy,
			ShardMergeStrategy shardMergeStrategy) {
		
		Assert.notNull(shardSelectionStrategy);
		Assert.notNull(shardResolutionStrategy);
		Assert.notNull(shardAccessStrategy);
		Assert.notNull(shardMergeStrategy);
		
		this.shardSelectionStrategy = shardSelectionStrategy;
		this.shardResolutionStrategy = shardResolutionStrategy;
		this.shardAccessStrategy = shardAccessStrategy;
		this.shardMergeStrategy = shardMergeStrategy;
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
	public ShardMergeStrategy getShardMergeStrategy() {
		return shardMergeStrategy;
	}
	
}
