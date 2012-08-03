package org.makersoft.shards.strategy.access;

import java.util.List;

import org.makersoft.shards.Shard;
import org.makersoft.shards.ShardOperation;
import org.makersoft.shards.strategy.exit.ExitOperationsCollector;
import org.makersoft.shards.strategy.exit.ExitStrategy;


/**
 * 数据抓取策略
 */
public interface ShardAccessStrategy {
	 <T> T apply(List<Shard> shards, ShardOperation<T> operation, ExitStrategy<T> exitStrategy, ExitOperationsCollector exitOperationsCollector);
}
