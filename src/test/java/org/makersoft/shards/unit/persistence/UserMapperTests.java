/*
 * @(#)UserMapperTests.java 2012-9-4 下午3:59:06
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.unit.persistence;

import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.makersoft.shards.domain.User;
import org.makersoft.shards.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
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
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("test")
public class UserMapperTests {

	@Autowired(required = true)
	private UserMapper userMapper;

	private static String firstId;

	public static int rowsCount = 10000;

	private Random random = new Random();

	@Test
	@Transactional
	public void testInsert() throws Exception {

		for (int i = 0; i < rowsCount; i++) {
			User user = new User();
			user.setUsername("makersoft" + i);
			user.setPassword("makersoft" + i);
			user.setAge(random.nextInt(30));

			if (i % 2 == 0) {
				user.setGender(User.SEX_MALE);
			} else {
				user.setGender(User.SEX_FEMALE);
			}

			userMapper.insertUser(user);

			if (i == 0) {
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
		int rows = userMapper.udpateUser(user);
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
		
		int rows = userMapper.updateById(user);
		Assert.assertEquals(1, rows);
	}
	
	@Test
	@Transactional(readOnly = true)
	public void testGetAllCount() throws Exception{
		int count = userMapper.getAllCount();
		Assert.assertEquals(rowsCount, count);
		long start = System.currentTimeMillis();
		count = userMapper.getAllCount();
		System.out.println("耗时：" +(System.currentTimeMillis() - start));
		Assert.assertEquals(rowsCount, count);
	}
	
	@Test
	@Transactional(readOnly = true)
	public void testFindAll() throws Exception{
		List<User> users = userMapper.findAll();
		Assert.assertEquals(rowsCount, users.size());
	}
	
	@Test
	@Transactional(readOnly = true)
	public void testFindByGender() throws Exception{
		List<User> users = userMapper.findByGender(User.SEX_MALE);
		Assert.assertEquals(rowsCount / 2, users.size());
	}
	
	@Test
	@Transactional(readOnly = true)
	public void testGetById() throws Exception{
		User user = userMapper.getById(firstId);
		Assert.assertNotNull(user);
	}
	
	@Test
	@Rollback
	@Transactional
	public void testDeleteById() throws Exception{
		
		int rows = userMapper.deleteById(firstId);
		Assert.assertEquals(1, rows);
	}
	
	@Test
	@Transactional
	public void testDelete() throws Exception{
		
		int rows = userMapper.deleteAll();
		Assert.assertEquals(rowsCount, rows);
	}

}
