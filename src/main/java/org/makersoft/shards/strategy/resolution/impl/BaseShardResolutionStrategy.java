package org.makersoft.shards.strategy.resolution.impl;

import java.util.List;

import org.makersoft.shards.BaseHasShardIdList;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.strategy.resolution.ShardResolutionStrategy;

/**
 * 
 */
public abstract class BaseShardResolutionStrategy extends BaseHasShardIdList
		implements ShardResolutionStrategy {

	public BaseShardResolutionStrategy(List<ShardId> shardIds) {
		super(shardIds);
	}
	
}