package org.makersoft.shards.unit.session;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.makersoft.shards.cfg.ShardConfiguration;
import org.makersoft.shards.cfg.impl.ShardConfigurationImpl;
import org.makersoft.shards.session.ShardedSqlSessionFactory;
import org.makersoft.shards.session.impl.ShardedSqlSessionFactoryBuilder;
import org.makersoft.shards.strategy.UserShardStrategyFactory;
import org.makersoft.shards.unit.BaseTest;

/**
 * test for build ShardedSqlSessionFactory
 */
public class ShardedSqlSessionFactoryTests extends BaseTest{

	private ShardedSqlSessionFactory factory;

	@Test
	public void testBuildSesssionFactory() throws Exception {
		List<ShardConfiguration> shardConfigs = new ArrayList<ShardConfiguration>();
		
		final String resource_0 = "mybatis/mybatis-config-0.xml";
		final String resource_1 = "mybatis/mybatis-config-1.xml";
		final Reader reader_0 = Resources.getResourceAsReader(resource_0);
		final Reader reader_1 = Resources.getResourceAsReader(resource_1);
		
		
		shardConfigs.add(new ShardConfigurationImpl(0, createDs1DataSource(), new SqlSessionFactoryBuilder().build(reader_0)));
		shardConfigs.add(new ShardConfigurationImpl(1, createDs2DataSource(), new SqlSessionFactoryBuilder().build(reader_1)));
		
		factory = new ShardedSqlSessionFactoryBuilder().build(shardConfigs, new UserShardStrategyFactory());
		
		Assert.assertNotNull(factory);
	}
}
