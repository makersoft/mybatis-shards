/*
 * @(#)UserMapperTests.java 2012-9-4 下午3:59:06
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.unit.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.makersoft.shards.domain.User;
import org.makersoft.shards.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * unit test for user mapper.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ActiveProfiles("test")
public class UserMapperTests {

	@Autowired(required = true)
	private UserMapper userMapper;

	private Random random = new Random();

	@Test
	@Transactional
	public void testInsert() throws Exception {

		for (int i = 0; i < 1000; i++) {
			String username = "makersoft_" + i;
			String password = "makersoft_" + i;
			int age = random.nextInt(30);
			int gender = (i % 2 == 0) ? User.SEX_MALE : User.SEX_FEMALE;
			
			User user = this.insertUser(username, password, age, gender);
			
			Assert.assertNotNull(user.getId());
		}
	}
	
	@Test
	@Transactional
	public void testUpdate() throws Exception{
		User user = this.insertUser("makersoft", "makersoft", 26, User.SEX_MALE);
		
		user.setUsername("www.makersoft.org");
		user.setPassword("www.makersoft.org");
		
		//update entity
		int rows = userMapper.udpateUser(user);
		
		Assert.assertEquals(1, rows);
	}
	
	@Test
	@Transactional
	public void testUpdateById() throws Exception {
		User user = this.insertUser("makersoft", "makersoft", 26, User.SEX_MALE);
		
		user.setUsername("username");
		user.setPassword("password");
		
		int rows = userMapper.updateById(user);
		Assert.assertEquals(1, rows);
	}
	
	@Test
	@Transactional
	public void testGetAllCount() throws Exception{
		int count = userMapper.getAllCount();
		Assert.assertEquals(0, count);
		
		for(int i = 0; i < 10; i++){
			String username = "makersoft_" + i;
			String password = "makersoft_" + i;
			int age = random.nextInt(30);
			int gender = (i % 2 == 0) ? User.SEX_MALE : User.SEX_FEMALE;
			
			User user = this.insertUser(username, password, age, gender);
			Assert.assertNotNull(user.getId());
		}
		
		count = userMapper.getAllCount();
		
		Assert.assertEquals(10, count);
	}
	
	@Test
	@Transactional
	public void testFindAll() throws Exception{
		Map<String, User> caches = new HashMap<String, User>();
		for(int i = 0; i < 10; i++){
			String username = "makersoft_" + i;
			String password = "makersoft_" + i;
			int age = random.nextInt(30);
			int gender = (i % 2 == 0) ? User.SEX_MALE : User.SEX_FEMALE;
			
			User user = this.insertUser(username, password, age, gender);
			Assert.assertNotNull(user.getId());
			
			caches.put(user.getId(), user);
		}
		
		
		List<User> users = userMapper.findAll();
		Assert.assertEquals(10, users.size());
		
		for(User entity : users) {
			User user = caches.get(entity.getId());
			Assert.assertNotNull(user);
			
			//has override hashCode and equals method
			Assert.assertEquals(entity, user);
		}
	}
	
	@Test
	@Transactional
	public void testFindByGender() throws Exception{
		for(int i = 0; i < 10; i++){
			String username = "makersoft_" + i;
			String password = "makersoft_" + i;
			int age = random.nextInt(30);
			int gender = (i % 2 == 0) ? User.SEX_MALE : User.SEX_FEMALE;
			
			User user = this.insertUser(username, password, age, gender);
			Assert.assertNotNull(user.getId());
		}
		
		List<User> users = userMapper.findByGender(User.SEX_MALE);
		Assert.assertEquals(5, users.size());
	}
	
	@Test
	@Transactional
	public void testGetById() throws Exception{
		User user = this.insertUser("makersoft", "makersoft", 26, User.SEX_MALE);
		User result = userMapper.getById(user.getId());
		
		Assert.assertNotNull(result);
		
		Assert.assertEquals(user, result);
	}
	
	@Test
	@Transactional
	public void testDeleteById() throws Exception{
		User user = this.insertUser("makersoft", "makersoft", 26, User.SEX_MALE);
		int rows = userMapper.deleteById(user.getId());
		Assert.assertEquals(1, rows);
		
		user = userMapper.getById(user.getId());
		
		Assert.assertNull(user);
		
	}
	
	@Test
	@Transactional
	public void testDelete() throws Exception{
		for(int i = 0; i < 10; i++){
			String username = "makersoft_" + i;
			String password = "makersoft_" + i;
			int age = random.nextInt(30);
			int gender = (i % 2 == 0) ? User.SEX_MALE : User.SEX_FEMALE;
			
			User user = this.insertUser(username, password, age, gender);
			Assert.assertNotNull(user.getId());
		}
		
		int rows = userMapper.deleteAll();
		Assert.assertEquals(10, rows);
		
		int count = userMapper.getAllCount();
		Assert.assertEquals(0, count);
	}
	
	/**
	 * for insert new user 
	 */
	private User insertUser(String username, String password, int age,  int gender){
		User user = new User(username, password, gender);
		user.setAge(age);
		userMapper.insertUser(user);
		
		Assert.assertNotNull(user.getId());
		
		return user;
	}

}
