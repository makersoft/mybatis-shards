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
