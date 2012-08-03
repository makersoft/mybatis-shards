package org.makersoft.shards.select.impl;

import java.util.List;
import java.util.Map;

import org.makersoft.shards.Shard;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.ShardOperation;
import org.makersoft.shards.select.SelectFactory;
import org.makersoft.shards.select.ShardSelect;
import org.makersoft.shards.strategy.access.ShardAccessStrategy;
import org.makersoft.shards.strategy.exit.impl.ConcatenateListsExitStrategy;
import org.makersoft.shards.strategy.exit.impl.ExitOperationsSelectListCollector;
import org.makersoft.shards.strategy.exit.impl.ExitOperationsSelectOneCollector;
import org.makersoft.shards.strategy.exit.impl.SelectOneExitStrategy;
import org.makersoft.shards.utils.Lists;
import org.makersoft.shards.utils.ParameterUtil;

/**
 * 
 */
public class ShardSelectImpl implements ShardSelect {

	private final List<Shard> shards;

	private final SelectFactory selectFactory;
	
	private final ShardAccessStrategy shardAccessStrategy;

	/**
	 * The queryCollector is not used in ShardedQueryImpl as it would require
	 * this implementation to parse the query string and extract which exit
	 * operations would be appropriate. This member is a place holder for future
	 * development.
	 */
	private final ExitOperationsSelectListCollector queryCollector;

	public ShardSelectImpl(List<Shard> shards, SelectFactory selectFactory,
			ShardAccessStrategy shardAccessStrategy) {
		this.shards = shards;
		this.selectFactory = selectFactory;
		this.shardAccessStrategy = shardAccessStrategy;
		this.queryCollector = new ExitOperationsSelectListCollector(
				selectFactory.getRowBounds());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> getResultList() {
		ShardOperation<List<Object>> shardOp = new ShardOperation<List<Object>>() {
			public List<Object> execute(Shard shard) {

				List<Object> result = Lists.newArrayList();
				// 多线程优化
				for (ShardId shardId : shard.getShardIds()) {
					result.addAll(shard.establishSqlSession().selectList(
							selectFactory.getStatement(),
							ParameterUtil.resolve(selectFactory.getParameter(),
									shardId), selectFactory.getRowBounds()));
				}

				return result;
			}

			public String getOperationName() {
				return "getResultList()";
			}
		};

		return (List<E>) shardAccessStrategy.apply(shards, shardOp,
				new ConcatenateListsExitStrategy(), queryCollector);
	}

	@Override
	public <K, V> Map<K, V> getResultMap() {
//		ShardOperation<Map<K, V>> shardOp = new ShardOperation<Map<K, V>>() {
//			public Map<K, V> execute(Shard shard) {
//
//				// return shard.establishSqlSession().selectMap(statement,
//				// parameter, mapKey);
//				return null;
//			}
//
//			public String getOperationName() {
//				return "getResultMap()";
//			}
//		};
		
		throw new UnsupportedOperationException();
		
//		return (T) shardAccessStrategy.apply(
//				shards,
//				shardOp,
//				new SelectOneExitStrategy(),
//				new ExitOperationsSelectMapCollector(selectFactory
//						.getStatement()));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getSingleResult() {

		ShardOperation<Object> shardOp = new ShardOperation<Object>() {
			public Object execute(Shard shard) {
				// 虚拟分区，多线程优化
				return shard.establishSqlSession().selectOne(
						selectFactory.getStatement(),
						selectFactory.getParameter());
			}

			public String getOperationName() {
				return "getSingleResult()";
			}
		};

		return (T) shardAccessStrategy.apply(
				shards,
				shardOp,
				new SelectOneExitStrategy(),
				new ExitOperationsSelectOneCollector(selectFactory
						.getStatement()));
	}

}
