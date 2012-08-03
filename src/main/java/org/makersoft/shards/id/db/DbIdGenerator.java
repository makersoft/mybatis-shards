package org.makersoft.shards.id.db;

import java.io.Serializable;

import org.apache.ibatis.session.SqlSession;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.id.IdGenerator;

/**
 * 
 */
public class DbIdGenerator implements IdGenerator {
	protected int idBlockSize = 0;
	protected long nextId = 0;
	protected long lastId = -1;
	
	protected short nodeId = 0;
	
	public DbIdGenerator(){
		
	}

	@Override
	public synchronized Serializable generate(SqlSession session, Object object) {
		if (lastId < nextId) {
			getNewBlock(session, object);
		}
		long _nextId = nextId++;
		return Long.toString(_nextId);
	}

	protected synchronized void getNewBlock(SqlSession session, Object object) {

		IdBlock idBlock = session.selectOne("", idBlockSize);
		// TODO http://jira.codehaus.org/browse/ACT-45 use a separate
		// 'requiresNew' command executor
		// IdBlock idBlock = commandExecutor.execute(new
		// GetNextIdBlockCmd(idBlockSize));
		this.nextId = idBlock.getNextId();
		this.lastId = idBlock.getLastId();
	}

	@Override
	public ShardId extractShardId(Serializable identifier) {
		return null;
	}

}
