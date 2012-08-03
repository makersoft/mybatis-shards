package org.makersoft.shards.strategy.resolution.impl;

import java.util.List;

import org.makersoft.shards.ShardId;
import org.makersoft.shards.strategy.resolution.ShardResolutionStrategyData;

/**
 * 
 */
public class AllShardsShardResolutionStrategy extends BaseShardResolutionStrategy{

	public AllShardsShardResolutionStrategy(List<ShardId> shardIds) {
		super(shardIds);
	}

	@Override
	public List<ShardId> selectShardIdsFromShardResolutionStrategyData(
			ShardResolutionStrategyData shardResolutionStrategyData) {
		
		return super.getShardIds();
	}

}
