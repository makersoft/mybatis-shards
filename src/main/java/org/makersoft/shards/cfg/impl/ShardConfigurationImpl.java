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
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.cfg.ShardConfiguration;
import org.makersoft.shards.id.IdGenerator;
import org.makersoft.shards.strategy.ShardStrategyFactory;
import org.makersoft.shards.utils.Lists;
import org.springframework.core.io.Resource;

/**
 * @version 2012-8-1 下午10:00:00
 * @author Feng Kuok
 */
public class ShardConfigurationImpl implements ShardConfiguration{
	
	private Integer shardId;
	
	private List<ShardId> shardIds;
	
	private DataSource dataSource;
	
	private SqlSessionFactory sqlSessionFactory;
	
	//
	private Resource configLocation;

	private Resource[] mapperLocations;

	private Properties configurationProperties;

	private boolean failFast;
	
	private Interceptor[] plugins;

	private TypeHandler<?>[] typeHandlers;

	private String typeHandlersPackage;

	private Class<?>[] typeAliases;

	private String typeAliasesPackage;
	
	//---
	private ShardStrategyFactory shardStrategyFactory;
	
	private IdGenerator idGenerator; 
	
	//constructor
	public ShardConfigurationImpl(){
	}
	
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

	//--------------------------------
	public Resource getConfigLocation() {
		return configLocation;
	}

	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	public Resource[] getMapperLocations() {
		return mapperLocations;
	}

	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	public Properties getConfigurationProperties() {
		return configurationProperties;
	}

	public void setConfigurationProperties(Properties configurationProperties) {
		this.configurationProperties = configurationProperties;
	}

	public boolean isFailFast() {
		return failFast;
	}

	public void setFailFast(boolean failFast) {
		this.failFast = failFast;
	}

	public Interceptor[] getPlugins() {
		return plugins;
	}

	public void setPlugins(Interceptor[] plugins) {
		this.plugins = plugins;
	}

	public TypeHandler<?>[] getTypeHandlers() {
		return typeHandlers;
	}

	public void setTypeHandlers(TypeHandler<?>[] typeHandlers) {
		this.typeHandlers = typeHandlers;
	}

	public String getTypeHandlersPackage() {
		return typeHandlersPackage;
	}

	public void setTypeHandlersPackage(String typeHandlersPackage) {
		this.typeHandlersPackage = typeHandlersPackage;
	}

	public Class<?>[] getTypeAliases() {
		return typeAliases;
	}

	public void setTypeAliases(Class<?>[] typeAliases) {
		this.typeAliases = typeAliases;
	}

	public String getTypeAliasesPackage() {
		return typeAliasesPackage;
	}

	public void setTypeAliasesPackage(String typeAliasesPackage) {
		this.typeAliasesPackage = typeAliasesPackage;
	}

	public ShardStrategyFactory getShardStrategyFactory() {
		return shardStrategyFactory;
	}

	public void setShardStrategyFactory(ShardStrategyFactory shardStrategyFactory) {
		this.shardStrategyFactory = shardStrategyFactory;
	}

	public IdGenerator getIdGenerator() {
		return idGenerator;
	}

	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}
	
	//用于单独配置每个Shard Configuration
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public void setShardId(Integer shardId) {
		this.shardId = shardId;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
