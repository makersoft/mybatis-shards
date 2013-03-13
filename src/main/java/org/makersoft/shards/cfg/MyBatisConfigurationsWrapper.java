/*
 * @(#)MyBatisConfigurationsWrapper.java 2013-3-9 下午23:33:33
 *
 * Copyright (c) 2011-2013 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.cfg;

import java.util.List;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 此类用于包裹MyBatis-Shards多个Shard合并后的Configuration.
 * (只重写用的到的几个方法)
 */
public class MyBatisConfigurationsWrapper extends Configuration {

	private final Configuration configuration;

	private final List<SqlSessionFactory> sqlSessionFactories;

	public MyBatisConfigurationsWrapper(Configuration configuration,
			List<SqlSessionFactory> sqlSessionFactories) {
		this.configuration = configuration;
		this.sqlSessionFactories = sqlSessionFactories;
	}

	@Override
	public Environment getEnvironment() {
		return configuration.getEnvironment();
	}

	@Override
	public ExecutorType getDefaultExecutorType() {
		return configuration.getDefaultExecutorType();
	}

	@Override
	public ObjectFactory getObjectFactory() {
		return configuration.getObjectFactory();
	}

	@Override
	public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
		for (SqlSessionFactory sqlSessionFactory : getSqlSessionFactories()) {
			T mapper = null;
			try {
				mapper = sqlSessionFactory.getConfiguration().getMapper(type, sqlSession);
			} catch (BindingException e) {
				// ignore exception
			}

			if (mapper != null) {
				return mapper;
			}
		}

		throw new BindingException("Type " + type + " is not known to the MapperRegistry.");

	}

	@Override
	public boolean hasMapper(Class<?> type) {
		for (SqlSessionFactory factory : getSqlSessionFactories()) {
			if (factory.getConfiguration().hasMapper(type)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public MappedStatement getMappedStatement(String id) {
		Exception exception = null;
		MappedStatement mappedStatement = null;
		for (SqlSessionFactory sqlSessionFactory : getSqlSessionFactories()) {
			try {
				mappedStatement = sqlSessionFactory.getConfiguration().getMappedStatement(id);
			} catch (Exception e) {
				// ignore exception
				exception = e;
			}

			if (mappedStatement != null) {
				return mappedStatement;
			}
		}

		throw new BindingException("Invalid bound statement (not found): " + id, exception);
	}

	private List<SqlSessionFactory> getSqlSessionFactories() {
		return sqlSessionFactories;
	}
}
