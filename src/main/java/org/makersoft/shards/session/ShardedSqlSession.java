/*
 * @(#)ShardedSqlSession.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.session;

import org.apache.ibatis.session.SqlSession;
import org.makersoft.shards.ShardId;

/**
 * 
 * @see SqlSession
 * @see ShardedSqlSessionFactory
 */
public interface ShardedSqlSession extends SqlSession{

	SqlSession getSqlSessionForStatement(String statement);
	
	ShardId getShardIdForStatementOrParameter(String statement, Object parameter);
	
//	void lockShard();
}
