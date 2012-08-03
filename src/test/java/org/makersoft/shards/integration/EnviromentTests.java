package org.makersoft.shards.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ActiveProfiles("test")
//@ProfileValueSourceConfiguration(SystemProfileValueSource.class)
//extends AbstractTransactionalJUnit4SpringContextTests
public class EnviromentTests extends AbstractJUnit4SpringContextTests{

	@Test
	public void testSpringContext(){
		Assert.assertNotNull(super.applicationContext);
	}
	
}
