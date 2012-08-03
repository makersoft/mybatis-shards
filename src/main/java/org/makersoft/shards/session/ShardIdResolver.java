package org.makersoft.shards.session;

import java.util.List;

import org.makersoft.shards.Shard;
import org.makersoft.shards.ShardId;

/**
 * Interface for statement that are able to resolve shard of statements.
 */
public interface ShardIdResolver {

	 /**
	   * Gets ShardId of the shard given object lives on. Only consideres given
	   * Shards.
	   *
	   * @param obj Object whose Shard should be resolved
	   * @param shardsToConsider Shards which should be considered during resolution
	   * @return ShardId of the shard the object lives on; null if shard could not be resolved
	   */
	  /*@Nullable*/ ShardId getShardIdForStatementOrParameter(String statement, Object parameter, List<Shard> shardsToConsider);

	  /**
	   * Gets ShardId of the shard given object lives on.
	   *
	   * @param obj Object whose Shard should be resolved
	   * @return ShardId of the shard the object lives on; null if shard could not be resolved
	   */
	  /*@Nullable*/ ShardId getShardIdForStatementOrParameter(String statement, Object parameter);
}
