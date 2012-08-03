package org.makersoft.shards.strategy.exit;

import org.makersoft.shards.Shard;

/**
 * 
 */
public interface ExitStrategy<T> {

	/**
	 * Add the provided result and return whether or not the caller can halt
	 * processing.
	 * 
	 * @param result
	 *            The result to add
	 * @return Whether or not the caller can halt processing
	 */
	boolean addResult(T result, Shard shard);

	T compileResults(ExitOperationsCollector exitOperationsCollector);

}
