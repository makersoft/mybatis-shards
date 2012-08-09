/*
 * @(#)ShardConfiguration.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.cfg.impl;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.cfg.ShardConfiguration;
import org.makersoft.shards.utils.Lists;

/**
 * @version 2012-8-1 下午10:00:00
 * @author Feng Kuok
 */
public class ShardConfigurationImpl implements ShardConfiguration{
	
	private final Integer shardId;
	
	private final List<ShardId> shardIds;
	
	private final DataSource dataSource;
	
	private final SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 物理分区和逻辑分区一对一
	 * @param shardId	分区Id
	 * @param dataSource	数据源
	 * @param sqlSessionFactory	 mybaits中的{@link SqlSessionFactory}
	 */
	public ShardConfigurationImpl(Integer shardId, DataSource dataSource, SqlSessionFactory sqlSessionFactory){
		this(shardId, Lists.newArrayList(new ShardId(shardId)), dataSource, sqlSessionFactory);
	}
	
	public ShardConfigurationImpl(Integer shardId, List<ShardId> shardIds, DataSource dataSource, SqlSessionFactory sqlSessionFactory){
		this.shardId = shardId;
		this.dataSource = dataSource;
		this.sqlSessionFactory = sqlSessionFactory;
		this.shardIds = shardIds;
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
