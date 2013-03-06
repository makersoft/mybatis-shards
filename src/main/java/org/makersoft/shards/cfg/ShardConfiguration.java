/*
 * @(#)ShardConfiguration.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.cfg;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.makersoft.shards.ShardId;

/**
 * 用于描述分区的配置信息，不同的物理分区可包含不同的{@link SqlSessionFactory}和多个逻辑分区{@link ShardId}
 * 
 * @version 2012-8-1 下午10:00:00
 * @author Feng Kuok
 */
public interface ShardConfiguration {

	/**
	 * @return 此物理分区的唯一ID
	 */
	Integer getShardId();

	/**
	 * @return 此物理分区下所属的所有虚拟分区
	 */
	List<ShardId> getShardIds();

	/**
	 * @return 此物理分区所对应的数据源
	 */
	DataSource getShardDataSource();

	/**
	 * @see SqlSessionFactory
	 * @return 此物理分区的{@link SqlSessionFactory}
	 */
	SqlSessionFactory getSqlSessionFactory();

//	void setConfigLocation(Resource configLocation);
//
//	/**
//	 * @return locations of MyBatis mapper files
//	 */
//	Resource[] getMapperLocations();
//
//	void getConfigurationProperties(Properties configurationProperties);
//
//	void getPlugins(Interceptor[] plugins);
//
//	void getTypeHandlers(TypeHandler<?>[] typeHandlers);
//
//	void getTypeHandlersPackage(String typeHandlersPackage);
//
//	void getTypeAliases(Class<?>[] typeAliases);
//
//	void getTypeAliasesPackage(String typeAliasesPackage);
//
//	void getShardStrategyFactory(ShardStrategyFactory shardStrategyFactory);
//
//	void getIdGenerator(IdGenerator idGenerator);
}
