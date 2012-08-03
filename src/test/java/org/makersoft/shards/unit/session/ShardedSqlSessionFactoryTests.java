package org.makersoft.shards.unit.session;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.makersoft.shards.session.ShardedSqlSessionFactory;

/**
 * 
 */
public class ShardedSqlSessionFactoryTests {

	ShardedSqlSessionFactory factory;
	
	@Before
	public void before() throws Exception{
//		SqlSessionFactory factory1 = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis/mybatis-config-0.xml"));
//		ShardedConfiguration configuration = new ShardedConfiguration(shardConfigs, shardStrategyFactory);
	}
	
	@Test
	public void testSave() throws Exception{
		Assert.assertNotNull(factory);
		
	}
}
