/*
 * @(#)ShardedSqlSessionFactoryTests.java 2012-8-7 下午3:55:58
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
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.makersoft.shards.cfg.ShardConfiguration;
import org.makersoft.shards.cfg.impl.ShardConfigurationImpl;
import org.makersoft.shards.session.ShardedSqlSessionFactory;
import org.makersoft.shards.session.impl.ShardedSqlSessionFactoryBuilder;
import org.makersoft.shards.strategy.UserShardStrategyFactory;

/**
 * test for build ShardedSqlSessionFactory
 */
public class ShardedSqlSessionFactoryTests{

	private ShardedSqlSessionFactory factory;

	@Test
	public void testBuildSesssionFactory() throws Exception {
//		System.setProperty("javax.xml.parsers.DocumentBuilderFactory","com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
		List<ShardConfiguration> shardConfigs = new ArrayList<ShardConfiguration>();
		
		final String resource_0 = "mybatis/mybatis-config-0.xml";
		final String resource_1 = "mybatis/mybatis-config-1.xml";
		final Reader reader_0 = Resources.getResourceAsReader(resource_0);
		final Reader reader_1 = Resources.getResourceAsReader(resource_1);
		
		SqlSessionFactory sqlMapper_0 = new SqlSessionFactoryBuilder().build(reader_0);
//		SqlSessionFactory sqlMapper_1 = new SqlSessionFactoryBuilder().build(reader_1);
//
//		shardConfigs.add(new ShardConfigurationImpl(0, sqlMapper_0.getConfiguration().getEnvironment().getDataSource(), sqlMapper_0));
//		shardConfigs.add(new ShardConfigurationImpl(1, sqlMapper_1.getConfiguration().getEnvironment().getDataSource(), sqlMapper_1));
//
//		factory = new ShardedSqlSessionFactoryBuilder().build(shardConfigs, new UserShardStrategyFactory());
//
//		Assert.assertNotNull(factory);
	}
}
