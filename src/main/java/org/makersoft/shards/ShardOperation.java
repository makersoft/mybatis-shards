package org.makersoft.shards;

/**
 * Simple interface used to reference something we can do against a {@link Shard}.
 */
public interface ShardOperation<T> {
	/**
	 * @param shard
	 *            the shard to execute against
	 * @return the result of the operation
	 */
	T execute(Shard shard);

	/**
	 * @return the name of the operation (useful for logging and debugging)
	 */
	String getOperationName();
}
