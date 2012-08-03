package org.makersoft.shards.strategy.access.impl;

import java.util.List;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.makersoft.shards.Shard;
import org.makersoft.shards.ShardOperation;
import org.makersoft.shards.strategy.access.ShardAccessStrategy;
import org.makersoft.shards.strategy.exit.ExitOperationsCollector;
import org.makersoft.shards.strategy.exit.ExitStrategy;

/**
 * 顺序访问策略
 */
public class SequentialShardAccessStrategy implements ShardAccessStrategy {

	private final Log log = LogFactory.getLog(getClass());

	public <T> T apply(List<Shard> shards, ShardOperation<T> operation,
			ExitStrategy<T> exitStrategy,
			ExitOperationsCollector exitOperationsCollector) {
		
		for (Shard shard : getNextOrderingOfShards(shards)) {
			if (exitStrategy.addResult(operation.execute(shard), shard)) {
				log.debug(String.format("Short-circuiting operation %s after execution against shard %s", operation.getOperationName(), shard));
				break;
			}
		}
		
		return exitStrategy.compileResults(exitOperationsCollector);
	}

	/**
	 * Override this method if you want to control the order in which the shards
	 * are operated on (this comes in handy when paired with exit strategies
	 * that allow early exit because it allows you to evenly distribute load).
	 * Deafult implementation is to just iterate in the same order every time.
	 * 
	 * @param shards
	 *            The shards we might want to reorder
	 * @return Reordered view of the shards.
	 */
	protected Iterable<Shard> getNextOrderingOfShards(List<Shard> shards) {
		return shards;
	}
}
