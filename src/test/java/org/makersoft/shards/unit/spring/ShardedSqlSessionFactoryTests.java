package org.makersoft.shards.unit.spring;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.makersoft.shards.session.ShardedSqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("test")
public class ShardedSqlSessionFactoryTests {

	@Autowired(required = true)
	private ShardedSqlSessionFactory factory;
	
	@Test
	public void test_factory() throws Exception{
		Assert.assertNotNull(factory);
	}
	
}
