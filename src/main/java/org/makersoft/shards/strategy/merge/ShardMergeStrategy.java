/*
 * @(#)ShardMergeStrategy.java 2012-8-31 下午4:06:43
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.merge;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

/**
 * 分区结果集合并策略.
 * 
 * @version 2012-8-31 下午4:06:43
 * @author Feng Kuok
 */
public interface ShardMergeStrategy {
	
	List<Object> merge(String statement, Object parameter, RowBounds rowBounds, List<Object> result);

	void setSqlSession(SqlSession sqlSession);

	void setCreateTablePrefix(String createTablePrefix);

	void setInsertPrefix(String insertPrefix);

	void setSelectPrefix(String selectPrefix);
	
	void setDropTablePrefix(String dropTablePrefix);
}
