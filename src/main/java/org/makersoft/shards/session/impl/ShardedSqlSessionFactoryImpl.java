/*
 * @(#)ShardedSqlSessionFactoryImpl.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.session.impl;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.id.IdGenerator;
import org.makersoft.shards.session.ShardedSqlSession;
import org.makersoft.shards.session.ShardedSqlSessionFactory;
import org.makersoft.shards.strategy.ShardStrategy;
import org.makersoft.shards.strategy.ShardStrategyFactory;
import org.makersoft.shards.utils.Assert;
import org.makersoft.shards.utils.Iterables;
import org.makersoft.shards.utils.Lists;
import org.makersoft.shards.utils.Maps;
import org.makersoft.shards.utils.Sets;

/**
 * 
 */
public class ShardedSqlSessionFactoryImpl implements ShardedSqlSessionFactory {
	
	private final Log log = LogFactory.getLog(getClass());
	
	// the id of the control shard
	private static final int CONTROL_SHARD_ID = 0;

	private final List<SqlSessionFactory> sqlSessionFactories;
	private final List<ShardId> shardIds;
	
	// map of SessionFactories used by this ShardedSessionFactory (might be a subset of all SessionFactories)
	private final Map<SqlSessionFactory, Set<ShardId>> sqlSessionFactoryShardIdMap;

	// map of all existing SessionFactories, used when creating a new ShardedSessionFactory for some subset of shards
	@SuppressWarnings("unused")
	private final Map<SqlSessionFactory, Set<ShardId>> fullSqlSessionFactoryShardIdMap;
	  
	private final ShardStrategy shardStrategy;
	
	private final IdGenerator idGenerator;
	
	// Reference to the SessionFactory we use for functionality that expects
	// data to live in a single, well-known location (like distributed sequences)
	private final SqlSessionFactory controlSqlSessionFactory;

	public ShardedSqlSessionFactoryImpl(
			Map<SqlSessionFactory, Set<ShardId>> sessionFactoryShardIdMap,
			ShardStrategyFactory shardStrategyFactory, IdGenerator idGenerator) {

		this.sqlSessionFactories = Lists.newArrayList(sessionFactoryShardIdMap.keySet());
		this.sqlSessionFactoryShardIdMap = Maps.newHashMap();
		this.fullSqlSessionFactoryShardIdMap = sessionFactoryShardIdMap;
		this.shardIds = Lists.newArrayList(Iterables.concat(sessionFactoryShardIdMap.values()));

		Set<ShardId> uniqueShardIds = Sets.newHashSet();
		SqlSessionFactory controlSqlSessionFactoryToSet = null;
		for (Map.Entry<SqlSessionFactory, Set<ShardId>> entry : sessionFactoryShardIdMap.entrySet()) {
			SqlSessionFactory implementor = entry.getKey();
			Assert.notNull(implementor);
			
			Set<ShardId> shardIdSet = entry.getValue();
			Assert.notNull(shardIdSet);
			Assert.notNull(!shardIdSet.isEmpty());
			for (ShardId shardId : shardIdSet) {
				// TODO(tomislav): we should change it so we specify control shard in configuration
		        if (shardId.getId() == CONTROL_SHARD_ID) {
		        	controlSqlSessionFactoryToSet = implementor;
		        }
		        if(!uniqueShardIds.add(shardId)) {
		        	final String msg = String.format("Cannot have more than one shard with shard id %d.", shardId.getId());
		        	log.error(msg);
		        	throw new RuntimeException(msg);
		        }
		        if (shardIds.contains(shardId)) {
		        	if (!this.sqlSessionFactoryShardIdMap.containsKey(implementor)) {
		        		this.sqlSessionFactoryShardIdMap.put(implementor, Sets.<ShardId>newHashSet());
		        	}
		        	this.sqlSessionFactoryShardIdMap.get(implementor).add(shardId);
		        }
			}
	    }
		
		this.controlSqlSessionFactory = controlSqlSessionFactoryToSet;
		
		this.shardStrategy = shardStrategyFactory.newShardStrategy(shardIds);
		
		this.idGenerator = idGenerator;
	}

	private SqlSessionFactory getAnyFactory() {
		return sqlSessionFactories.get(0);
	}
	  
	@Override
	public List<SqlSessionFactory> getSqlSessionFactories() {
		return Collections.<SqlSessionFactory> unmodifiableList(sqlSessionFactories);
	}

	@Override
	public ShardedSqlSessionFactory getSqlSessionFactory(
			List<ShardId> shardIds, ShardStrategyFactory shardStrategyFactory) {
		throw new UnsupportedOperationException();
	}

	public SqlSession openControlSession() {
		Assert.notNull(controlSqlSessionFactory);
		
		SqlSession session = controlSqlSessionFactory.openSession();
	    return  session;
	}
	
	@Override
	public ShardedSqlSession openSession() {
		return this.openSession(false);
	}

	@Override
	public ShardedSqlSession openSession(boolean autoCommit) {
		return this.openSession(ExecutorType.SIMPLE, autoCommit);
	}

	@Override
	public ShardedSqlSession openSession(ExecutorType execType) {
		return this.openSession(execType, false);
	}
	
	@Override
	public ShardedSqlSession openSession(ExecutorType execType, boolean autoCommit) {
		return new ShardedSqlSessionImpl(this, shardStrategy);
	}
	
	@Override
	public ShardedSqlSession openSession(TransactionIsolationLevel level) {
		return this.openSession(ExecutorType.SIMPLE, level);
	}
	
	@Override
	public ShardedSqlSession openSession(ExecutorType execType,
			TransactionIsolationLevel level) {
		return new ShardedSqlSessionImpl(this, shardStrategy);
	}
	
	@Override
	public ShardedSqlSession openSession(Connection connection) {
		throw new UnsupportedOperationException(
				"Cannot open a sharded session with a user provided connection.");
	}

	@Override
	public ShardedSqlSession openSession(ExecutorType execType,
			Connection connection) {
		throw new UnsupportedOperationException(
				"Cannot open a sharded session with a user provided connection.");
	}

	@Override
	public Configuration getConfiguration() {
		return getAnyFactory().getConfiguration();
	}
	
	@Override
	public IdGenerator getIdGenerator() {
		return idGenerator;
	}

	@Override
	public Map<SqlSessionFactory, Set<ShardId>> getSqlSessionFactoryShardIdMap() {
		return sqlSessionFactoryShardIdMap;
	}

}
