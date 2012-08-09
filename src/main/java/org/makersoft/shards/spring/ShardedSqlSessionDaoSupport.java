/*
 * @(#)ShardedSqlSessionDaoSupport.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.spring;

import java.io.Serializable;

import org.makersoft.shards.utils.ReflectionUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * 
 */
public abstract class ShardedSqlSessionDaoSupport<T,PK extends Serializable> extends SqlSessionDaoSupport {
	
	protected final Class<T> entityClass;
	
	protected final String selectOneStatement;
	
	protected final String insertStatement;
	protected final String deleteStatement;
	
	public ShardedSqlSessionDaoSupport(){
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
		this.selectOneStatement = "select" + entityClass.getName();
		this.insertStatement = "insert" + entityClass.getName();
		this.deleteStatement = "delete" + entityClass.getName();
	}
	
	protected abstract String getNamespace();
	
	//jpa 
	public T find(Class<T> entityClass, PK primaryKey) {
		return super.getSqlSession().<T>selectOne(selectOneStatement, primaryKey);
	}
	
	public T merge(T entity) {
		return null;
	}
	
	public void persist(T entity) {
		super.getSqlSession().insert(insertStatement, entity);
	}
	
	public void remove(Object entity) {
		super.getSqlSession().delete(deleteStatement, entity);
	}
	
}
