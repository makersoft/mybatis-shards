/*
 * @(#)ShardedSqlSessionTests.java 2012-8-7 下午3:55:58
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.unit.session;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.cfg.ShardConfiguration;
import org.makersoft.shards.cfg.impl.ShardConfigurationImpl;
import org.makersoft.shards.domain.User;
import org.makersoft.shards.session.impl.ShardedSqlSessionFactoryBuilder;
import org.makersoft.shards.strategy.ShardStrategy;
import org.makersoft.shards.strategy.ShardStrategyFactory;
import org.makersoft.shards.strategy.ShardStrategyImpl;
import org.makersoft.shards.strategy.UserShardStrategyFactory;
import org.makersoft.shards.strategy.access.ShardAccessStrategy;
import org.makersoft.shards.strategy.access.impl.SequentialShardAccessStrategy;
import org.makersoft.shards.strategy.reduce.ShardReduceStrategy;
import org.makersoft.shards.strategy.resolution.ShardResolutionStrategy;
import org.makersoft.shards.strategy.resolution.impl.AllShardsShardResolutionStrategy;
import org.makersoft.shards.strategy.selection.ShardSelectionStrategy;
import org.makersoft.shards.unit.BaseTest;

/**
 * Unit test for <p>ShardedSqlSession</p>
 * 
 * @version 2012-8-7
 * @author Feng Kuok
 */
public class ShardedSqlSessionTests extends BaseTest{
	private static SqlSessionFactory factory;

	  @BeforeClass
	  public static void setup() throws Exception {
		  List<ShardConfiguration> shardConfigs = new ArrayList<ShardConfiguration>();
			
		  final String resource_0 = "mybatis/mybatis-config-0.xml";
		  final String resource_1 = "mybatis/mybatis-config-1.xml";
		  final String resource_2 = "mybatis/mybatis-config-2.xml";
		  final Reader reader_0 = Resources.getResourceAsReader(resource_0);
		  final Reader reader_1 = Resources.getResourceAsReader(resource_1);
		  final Reader reader_2 = Resources.getResourceAsReader(resource_2);
			
		  SqlSessionFactory sqlMapper_0 = new SqlSessionFactoryBuilder().build(reader_0);
		  SqlSessionFactory sqlMapper_1 = new SqlSessionFactoryBuilder().build(reader_1);
		  SqlSessionFactory sqlMapper_2 = new SqlSessionFactoryBuilder().build(reader_2);
			
		  shardConfigs.add(new ShardConfigurationImpl(0, sqlMapper_0.getConfiguration().getEnvironment().getDataSource(), sqlMapper_0));
		  shardConfigs.add(new ShardConfigurationImpl(1, sqlMapper_1.getConfiguration().getEnvironment().getDataSource(), sqlMapper_1));
		  shardConfigs.add(new ShardConfigurationImpl(2, sqlMapper_2.getConfiguration().getEnvironment().getDataSource(), sqlMapper_2));
			
		  factory = new ShardedSqlSessionFactoryBuilder().build(shardConfigs, new UserShardStrategyFactory());
			
		  Assert.assertNotNull(factory);
			
		  factory = new ShardedSqlSessionFactoryBuilder().build(shardConfigs, new ShardStrategyFactory() {
			  
			  @Override
			  public ShardStrategy newShardStrategy(List<ShardId> shardIds) {
				  ShardSelectionStrategy pss = new ShardSelectionStrategy() {
					
					@Override
					public ShardId selectShardIdForNewObject(String statement, Object obj) {
						return null;
					}
				  };
				  
				  ShardResolutionStrategy prs = new AllShardsShardResolutionStrategy(shardIds);
				  ShardAccessStrategy pas = new SequentialShardAccessStrategy();
				  
				  ShardReduceStrategy srs = new ShardReduceStrategy() {
					
					@Override
					public List<Object> reduce(String statement, Object parameter, RowBounds rowBounds,
							List<Object> values) {
						return values;
					}
				  };
				  
				  return new ShardStrategyImpl(pss, prs, pas, srs);
			  }
		  });
	  }
	  
	  @Test
	  public void testSelectAllUsers() throws Exception {
		  SqlSession session = factory.openSession();
		  try {
			  List<User> users = session.selectList("findAllUsers");
			  Assert.assertEquals(5000, users.size());
		  } finally {
			  session.close();
		  }
	  }
}
