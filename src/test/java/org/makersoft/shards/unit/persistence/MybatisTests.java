package org.makersoft.shards.unit.persistence;

import java.util.List;

import junit.framework.Assert;

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
public class MybatisTests {
	
	@Autowired(required=true)
	private UserDao userMapper;
	
	@Test
//	@Rollback
	@Transactional
	public void testInsert() throws Exception{
		User user1 = new User("makersoft","makersoft",User.SEX_MALE);
		User user2 = new User("guofeng2","guofeng2",User.SEX_FEMALE);
		User user3 = new User("guofeng3","guofeng3",User.SEX_MALE);
		User user4 = new User("guofeng4","guofeng4",User.SEX_FEMALE);
		User user5 = new User("guofeng5","guofeng5",User.SEX_MALE);
		User user6 = new User("guofeng6","guofeng6",User.SEX_FEMALE);
		User user7 = new User("guofeng7","guofeng7",User.SEX_MALE);
		User user8 = new User("guofeng8","guofeng8",User.SEX_FEMALE);
		User user9 = new User("guofeng9","guofeng9",User.SEX_MALE);
		User user10 = new User("guofeng10","guofeng10",User.SEX_CONFIDENTIALITY);
		
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
	
	@Test
//	@Rollback
	@Transactional
	public void testUpdate() throws Exception{
		User user1 = new User("makersoft","www.makersoft.org",User.SEX_MALE);
//		User user2 = new User(2L,"guofeng2","123",User.SEX_FEMALE);
//		User user3 = new User(3L,"guofeng3","456",User.SEX_MALE);
//		User user4 = new User(4L,"guofeng4","789",User.SEX_FEMALE);
//		User user5 = new User(5L,"guofeng5","012",User.SEX_FEMALE);
		
		int rows = userMapper.udpateUser(user1);
		Assert.assertEquals(5, rows);
//		userMapper.udpateUser(user2);
//		userMapper.udpateUser(user3);
//		userMapper.udpateUser(user4);
//		userMapper.udpateUser(user5);
	}
	
	@Test
	@Transactional
	public void testUpdateById() throws Exception {
		User user1 = new User("makersoft","www.makersoft.org",User.SEX_MALE);
	}
	
	@Test
//	@Rollback
	@Transactional
	public void testDelete() throws Exception{
		
		int rows = userMapper.deleteById(4L);
		Assert.assertEquals(1, rows);
	}
	
	@Test
	@Transactional(readOnly = true)
	public void testFindAll() throws Exception{
		List<User> users = userMapper.findAll();
		Assert.assertEquals(5, users.size());
	}
	
	@Test
	public void testGetAllCount() throws Exception{
		int count = userMapper.getAllCount();
		Assert.assertEquals(5, count);
	}

}
