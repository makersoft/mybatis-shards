package org.makersoft.shards.strategy.exit.impl;

import java.util.List;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.makersoft.shards.Shard;
import org.makersoft.shards.strategy.exit.ExitOperationsCollector;
import org.makersoft.shards.strategy.exit.ExitStrategy;
import org.makersoft.shards.utils.Lists;

/**
 * 
 */
public class SelectOneExitStrategy implements ExitStrategy<Object> {

	private final List<Object> result = Lists.newArrayList();

	@Override
	public synchronized boolean addResult(Object oneResult, Shard shard) {
		result.add(oneResult);
		return false;
	}

	@Override
	public Object compileResults(ExitOperationsCollector exitOperationsCollector) {
		List<Object> list = exitOperationsCollector.apply(result);

		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() > 1) {
			throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
		} else {
			return null;
		}
	}

}
