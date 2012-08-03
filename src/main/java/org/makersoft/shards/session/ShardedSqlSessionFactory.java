package org.makersoft.shards.session;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSessionFactory;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.id.IdGenerator;
import org.makersoft.shards.strategy.ShardStrategyFactory;

/**
 * 
 */
public interface ShardedSqlSessionFactory extends SqlSessionFactory {

	List<SqlSessionFactory> getSqlSessionFactories();

	ShardedSqlSessionFactory getSqlSessionFactory(List<ShardId> shardIds,
			ShardStrategyFactory shardStrategyFactory);

	ShardedSqlSession openSession();
	
	IdGenerator getIdGenerator();

	Map<SqlSessionFactory, Set<ShardId>> getSqlSessionFactoryShardIdMap();

}
