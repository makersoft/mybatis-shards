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
