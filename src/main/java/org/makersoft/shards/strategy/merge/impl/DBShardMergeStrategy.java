/*
 * @(#)DBShardMergeStrategy.java 2012-8-31 下午4:11:56
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.merge.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.makersoft.shards.strategy.merge.ShardMergeStrategy;

/**
 * 基于数据库的合并策略.
 * 
 * @version 2012-8-31 下午4:11:56
 * @author Feng Kuok
 */
public class DBShardMergeStrategy implements ShardMergeStrategy{

	private SqlSession sqlSession;
	
	private String createTablePrefix = "CreateTable";
	
	private String insertPrefix = "Insert";
	
	private String selectPrefix = "Select";
	
	private String dropTablePrifix = "DropTable";
	
	@Override
	public List<Object> merge(String statement, Object parameter, RowBounds rowBounds, List<Object> result) {
		List<Object> results;	//return value
		
		String createTableStatement = statement + createTablePrefix;
		String insertStatement = statement + insertPrefix;
		String selectStatement = statement + selectPrefix;
		String dropTableStatement = statement + dropTablePrifix;
		
		//创建临时表
		sqlSession.update(createTableStatement);
		
		for(Object param : result){
			//插入分区查询结果集
			sqlSession.insert(insertStatement, param);
		}
		
		//二次查询结果集
		if(parameter == null){
			results = sqlSession.selectList(selectStatement, rowBounds);
		}else {
			results = sqlSession.selectList(selectStatement, parameter, rowBounds);
		}
		
		//删除临时表
		sqlSession.update(dropTableStatement);
		
		return results;
	}
	
	@Override
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void setCreateTablePrefix(String createTablePrefix){
		this.createTablePrefix = createTablePrefix;
	}
	
	@Override
	public void setInsertPrefix(String insertPrefix){
		this.insertPrefix = insertPrefix;
	}
	
	@Override
	public void setSelectPrefix(String selectPrefix){
		this.selectPrefix = selectPrefix;
	}

	@Override
	public void setDropTablePrefix(String dropTablePrefix) {
		this.dropTablePrifix = dropTablePrefix;
	}
	
}
