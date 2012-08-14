package org.makersoft.shards.unit.transaction;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 多数据源事务管理器单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "multiDataSourcesTransactionManager", defaultRollback = false)
@ActiveProfiles("test")
public class MultipleDataSourcesTransactionManagerTests {

	@Autowired
	@Qualifier("jdbcTemplate_1")
	private JdbcTemplate jdbcTemplate_1;
	
	@Autowired
	@Qualifier("jdbcTemplate_2")
	private JdbcTemplate jdbcTemplate_2;
	
	@Test
	@Transactional(rollbackFor={Exception.class,RuntimeException.class})
	@Rollback
	public void testTransactionCommitAndRollback() throws Exception{

		int affectedrows = jdbcTemplate_1.update("insert into maker_shards_user (id,username,password,gender) values (1,'makersoft','makersoft',1)");
		Assert.assertEquals(affectedrows, 1);
		affectedrows = jdbcTemplate_2.update("insert into maker_shards_user (id,username,password,gender) values (1,'makersoft','makersoft',0)");
		Assert.assertEquals(affectedrows, 1);

	}
	
}
