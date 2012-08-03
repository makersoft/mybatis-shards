package org.makersoft.shards.cfg.impl;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.cfg.ShardConfiguration;
import org.makersoft.shards.utils.Lists;

/**
 * 
 */
public class ShardConfigurationImpl implements ShardConfiguration{
	
	private final Integer shardId;
	
	private final List<ShardId> shardIds;
	
	private final DataSource dataSource;
	
	private final SqlSessionFactory sqlSessionFactory;
	
	public ShardConfigurationImpl(Integer shardId, DataSource dataSource, SqlSessionFactory sqlSessionFactory){
		this.shardId = shardId;
		this.dataSource = dataSource;
		this.sqlSessionFactory = sqlSessionFactory;
		shardIds = Lists.newArrayList(new ShardId(shardId));
	}

	@Override
	public Integer getShardId() {
		return shardId;
	}

	@Override
	public List<ShardId> getShardIds() {
		return shardIds;
	}

	@Override
	public DataSource getShardDataSource() {
		return dataSource;
	}

	@Override
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

}
