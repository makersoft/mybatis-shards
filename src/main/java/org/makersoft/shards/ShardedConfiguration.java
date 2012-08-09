/*
 * @(#)ShardedConfiguration.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.makersoft.shards.cfg.ShardConfiguration;
import org.makersoft.shards.id.IdGenerator;
import org.makersoft.shards.session.ShardedSqlSessionFactory;
import org.makersoft.shards.session.impl.ShardedSqlSessionFactoryImpl;
import org.makersoft.shards.strategy.ShardStrategyFactory;
import org.makersoft.shards.utils.Maps;
import org.springframework.util.Assert;

/**
 * 
 */
public class ShardedConfiguration {

	private final Log log = LogFactory.getLog(getClass());

	// private final

	// 分区配置
	private final List<ShardConfiguration> shardConfigs;

	// 用户自定义分区策略
	private final ShardStrategyFactory shardStrategyFactory;
	
	private final IdGenerator idGenerator;

	// 虚拟分区ids --> 物理分区 ids 映射
	private final Map<Integer, Integer> virtualShardToShardMap;

	// 物理分区ids --> 虚拟分区ids集合 映射
	private final Map<Integer, Set<ShardId>> shardToVirtualShardIdMap;
	

	public ShardedConfiguration(List<ShardConfiguration> shardConfigs,
			ShardStrategyFactory shardStrategyFactory, IdGenerator idGenerator) {

		this(shardConfigs, shardStrategyFactory, new HashMap<Integer, Integer>(), idGenerator);
	}

	public ShardedConfiguration(List<ShardConfiguration> shardConfigs,
			ShardStrategyFactory shardStrategyFactory,
			Map<Integer, Integer> virtualShardToShardMap, IdGenerator idGenerator) {
		Assert.notNull(shardConfigs);
		Assert.notNull(shardStrategyFactory);
		Assert.notNull(virtualShardToShardMap);

		this.shardConfigs = shardConfigs;
		this.shardStrategyFactory = shardStrategyFactory;
		this.virtualShardToShardMap = virtualShardToShardMap;
		this.idGenerator = idGenerator;

		if (!virtualShardToShardMap.isEmpty()) {
			// build the map from shard to set of virtual shards
			shardToVirtualShardIdMap = Maps.newHashMap();
			for (Map.Entry<Integer, Integer> entry : virtualShardToShardMap
					.entrySet()) {
				Set<ShardId> set = shardToVirtualShardIdMap.get(entry
						.getValue());
				// see if we already have a set of virtual shards
				if (set == null) {
					// we don't, so create it and add it to the map
					set = new HashSet<ShardId>();
					shardToVirtualShardIdMap.put(entry.getValue(), set);
				}
				set.add(new ShardId(entry.getKey()));
			}
		} else {
			shardToVirtualShardIdMap = new HashMap<Integer, Set<ShardId>>();
		}
	}

	public ShardedSqlSessionFactory buildShardedSessionFactory() {
		Map<SqlSessionFactory, Set<ShardId>> sqlSessionFactories = new HashMap<SqlSessionFactory, Set<ShardId>>();

		for (ShardConfiguration config : shardConfigs) {
			// populatePrototypeWithVariableProperties(config);
			// get the shardId from the shard-specific config
			Integer shardId = config.getShardId();
			if (shardId == null) {
				final String msg = "Attempt to build a ShardedSessionFactory using a "
						+ "ShardConfiguration that has a null shard id.";
				log.fatal(msg);
				throw new NullPointerException(msg);
			}
			Set<ShardId> virtualShardIds;
			if (virtualShardToShardMap.isEmpty()) {
				// simple case, virtual and physical are the same
				virtualShardIds = Collections.singleton(new ShardId(shardId));
			} else {
				// get the set of shard ids that are mapped to the physical
				// shard
				// described by this config
				virtualShardIds = shardToVirtualShardIdMap.get(shardId);
			}
			// sqlSessionFactories.put(buildSessionFactory(), virtualShardIds);
			sqlSessionFactories.put(config.getSqlSessionFactory(), virtualShardIds);
		}
		// final boolean doFullCrossShardRelationshipChecking =
		// PropertiesHelper.getBoolean(
		// ShardedEnvironment.CHECK_ALL_ASSOCIATED_OBJECTS_FOR_DIFFERENT_SHARDS,
		// prototypeConfiguration.getProperties(),
		// true);
		return new ShardedSqlSessionFactoryImpl(sqlSessionFactories,
				shardStrategyFactory, idGenerator);
	}

}
