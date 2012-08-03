package org.makersoft.shards.cfg;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.makersoft.shards.ShardId;

/**
 * 
 */
public interface ShardConfiguration {
//	/**
//	 * @return the name that the {@link org.hibernate.SessionFactory} created
//	 *         from this config will have
//	 */
//	String getShardSessionFactoryName();

	/**
	 * @return unique id of the shard
	 */
	Integer getShardId();
	
	List<ShardId> getShardIds();

	/**
	 * @return the datasource for the shard
	 */
	DataSource getShardDataSource();

//	/**
//	 * @see org.hibernate.cfg.Environment#CACHE_REGION_PREFIX
//	 * @return the cache region prefix for the shard
//	 */
//	String getShardCacheRegionPrefix();
	
	SqlSessionFactory getSqlSessionFactory();
}
