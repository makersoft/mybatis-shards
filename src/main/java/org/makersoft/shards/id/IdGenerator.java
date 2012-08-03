package org.makersoft.shards.id;

import java.io.Serializable;

import org.apache.ibatis.session.SqlSession;
import org.makersoft.shards.ShardId;

/**
 * 
 */
public interface IdGenerator {

	 Serializable generate(SqlSession session, Object object);
	 
	 ShardId extractShardId(Serializable identifier);
	 
}
