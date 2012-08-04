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
public class UserDaoTests {

	@Autowired(required=true)
	private UserDao userDao;
	
	public static int rowsCount = 50;
	
	public static String firstId;
	
	@Test
	@Transactional
	public void testInsert() throws Exception{
		
		for(int i = 0; i < rowsCount; i++){
			User user = new User();
			user.setUsername("makersoft" + i);
			user.setPassword("makersoft" + i);
			
			if(i % 2 == 0){
				user.setSex(User.SEX_MALE);
			}else{
				user.setSex(User.SEX_FEMALE);
			}
			
			userDao.insertUser(user);
			
			if(i == 0){
				firstId = user.getId();
				System.out.println(firstId);
			}
		}
	}
	
	@Test
	@Transactional
	public void testUpdate() throws Exception{
		User user = new User();
		user.setPassword("www.makersoft.org");
		int rows = userDao.udpateUser(user);
		Assert.assertEquals(rowsCount, rows);
	}
	
	@Test
	@Transactional
	public void testUpdateById() throws Exception {
		Assert.assertNotNull(firstId);

		User user = new User();
		user.setId(firstId);	
		user.setUsername("username");
		user.setPassword("password");
		
		int rows = userDao.updateById(user);
		Assert.assertEquals(1, rows);
	}
	
	@Test
	@Transactional(readOnly = true)
	public void testGetAllCount() throws Exception{
		int count = userDao.getAllCount();
		Assert.assertEquals(rowsCount, count);
	}
	
	@Test
	@Transactional(readOnly = true)
	public void testFindAll() throws Exception{
		List<User> users = userDao.findAll();
		Assert.assertEquals(rowsCount, users.size());
	}
	
	@Test
	@Transactional(readOnly = true)
	public void testGetById() throws Exception{
		User user = userDao.getById(firstId);
		Assert.assertNotNull(user);
	}
	
	@Test
	@Transactional
	public void testDeleteById() throws Exception{
		
		int rows = userDao.deleteById(firstId);
		Assert.assertEquals(1, rows);
	}
	
	@Test
	@Transactional
	public void testDelete() throws Exception{
		
		int rows = userDao.deleteAll();
		Assert.assertEquals(rowsCount - 1, rows);
	}
}
