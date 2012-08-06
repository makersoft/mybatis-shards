package org.makersoft.shards;

import org.apache.ibatis.session.SqlSession;

/**
 * Simple interface used to reference something we can do against a {@link Shard}.
 */
public interface ShardOperation<T> {

	/**
	 * @param session	the seesion of the shard to execute	
	 * @param shardId	the shardId to execute against
	 * @return	the result of the operation
	 */
	T execute(SqlSession session, ShardId shardId);

	/**
	 * @return the name of the operation (useful for logging and debugging)
	 */
	String getOperationName();
}
