/*
 * @(#)VerticalShardsTests.java 2013-3-7 下午23:33:33
 *
 * Copyright (c) 2011-2013 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.unit.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.makersoft.shards.domain.Role;
import org.makersoft.shards.domain.User;
import org.makersoft.shards.mapper.RoleMapper;
import org.makersoft.shards.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * unit test for vertical shards.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-vertical.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("test")
public class VerticalShardsTests {

	@Autowired(required = true)
	private UserMapper userMapper;

	@Autowired(required = true)
	private RoleMapper roleMapper;

	@Test
	@Transactional
	public void testInsert() throws Exception {

		User user = new User();
		user.setUsername("makersoft-vertical");
		user.setPassword("makersoft-vertical");
		user.setAge(18);
		user.setGender(User.SEX_MALE);

		userMapper.insertUser(user);
		
		Role role = new Role();
		role.setCode("ROLE_ADMIN");
		role.setName("ADMIN");
		roleMapper.insert(role);
	}

}
