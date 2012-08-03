package org.makersoft.shards.id.uuid;

import java.io.Serializable;

import org.apache.ibatis.session.SqlSession;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.id.IdGenerator;
import org.makersoft.shards.session.impl.ShardedSqlSessionImpl;
import org.springframework.util.Assert;

/**
 * 32位UUID
 */
public class ShardedUUIDGenerator extends UUIDHexGenerator implements
		IdGenerator {

	// private static String ZERO_STRING = "00000000000000000000000000000000";
	// private static String ID_TYPE_PROPERTY = "sharded-uuid-type";

	private int getShardId() {
		ShardId shardId = ShardedSqlSessionImpl.getCurrentSubgraphShardId();
		Assert.notNull(shardId);

		return Integer.valueOf(shardId.getId());
	}

	@Override
	public Serializable generate(SqlSession session, Object object) {
		String id = new StringBuilder(32).append(format((short) getShardId()))
				.append(format(getIP()))
				.append(format((short) (getJVM() >>> 16)))
				.append(format(getHiTime()))
				.append(format(getLoTime()))
				.append(format(getCount())).toString();

		return id;
	}

	@Override
	public ShardId extractShardId(Serializable identifier) {
		String hexId = (String) identifier;
		return new ShardId(Integer.decode("0x" + hexId.substring(0, 4)));
	}

	public static void main(String[] args) throws Exception {
		ShardedSqlSessionImpl.setCurrentSubgraphShardId(new ShardId(0));

		IdGenerator gen = new ShardedUUIDGenerator();
		IdGenerator gen2 = new ShardedUUIDGenerator();

		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			String id = (String) gen.generate(null, null);
			System.out.println(id);
			String id2 = (String) gen2.generate(null, null);
			System.out.println(id2);
		}

	}

}
