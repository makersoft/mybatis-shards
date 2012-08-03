package org.makersoft.shards.unit.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.makersoft.shards.domain.User;
import org.makersoft.shards.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "multiDataSourcesTransactionManager", defaultRollback = false)
@ActiveProfiles("test")
public class UserDaoTests {

	@Autowired(required=true)
	private UserDao userMapper;
	
	@Test
	@Transactional
	public void testInsert() throws Exception{
		User user1 = new User("makersoft1","makersoft1",User.SEX_MALE);
		User user2 = new User("makersoft2","makersoft2",User.SEX_FEMALE);
		User user3 = new User("makersoft3","makersoft3",User.SEX_MALE);
		User user4 = new User("makersoft4","makersoft4",User.SEX_FEMALE);
		User user5 = new User("makersoft5","makersoft5",User.SEX_MALE);
		User user6 = new User("makersoft6","makersoft6",User.SEX_FEMALE);
		User user7 = new User("makersoft7","makersoft7",User.SEX_MALE);
		User user8 = new User("makersoft8","makersoft8",User.SEX_FEMALE);
		User user9 = new User("makersoft9","makersoft9",User.SEX_CONFIDENTIALITY);
		User user10 = new User("makersoft10","makersoft10",User.SEX_CONFIDENTIALITY);
		
		userMapper.insertUser(user1);
		userMapper.insertUser(user2);
		userMapper.insertUser(user3);
		userMapper.insertUser(user4);
		userMapper.insertUser(user5);
		userMapper.insertUser(user6);
		userMapper.insertUser(user7);
		userMapper.insertUser(user8);
		userMapper.insertUser(user9);
		userMapper.insertUser(user10);
	}
}
