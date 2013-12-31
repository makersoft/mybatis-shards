/*
 * @(#)VerticalShardsTests.java 2013-3-7 下午23:33:33
 *
 * Copyright (c) 2011-2013 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.unit.persistence;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.makersoft.shards.domain.Role;
import org.makersoft.shards.domain.User;
import org.makersoft.shards.mapper.RoleMapper;
import org.makersoft.shards.mapper.UserMapper;
import org.makersoft.shards.utils.Lists;
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
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ActiveProfiles("test")
public class VerticalShardsTests {

	@Autowired(required = true)
	private UserMapper userMapper;

	@Autowired(required = true)
	private RoleMapper roleMapper;

	@Test
	@Transactional
	public void testInsert() throws Exception {

		User user = this.insertUser("makersoft-vertical", "makersoft-vertical", 18, User.SEX_MALE);

		assertNotNull("Insert User does not success!", user);
		
		
		Role role = this.insertRole("ADMIN", "ROLE_ADMIN");

		assertNotNull("Insert Role does not success!", role);
	}

	@Test
	@Transactional(readOnly = true)
	public void testSelect() throws Exception {
		List<User> maleUsers = Lists.newArrayList();

		for(int i = 0; i < 10; i++){
			String name = "makersoft-vertical_" + i;
			String password = "makersoft-vertical_" + i;
			int age = i;
			int gender = (i % 2) == 0 ? User.SEX_MALE : User.SEX_FEMALE;

			User user = this.insertUser(name, password, age, gender);
			
			if(gender == User.SEX_MALE){
				maleUsers.add(user);
			}
			
		}
		
		List<User> users = userMapper.findByGender(User.SEX_MALE);
		assertTrue("find by gender total size error", users.size() == 5);
		
		assertTrue(maleUsers.containsAll(users));
		
		
		List<Role> allRoles = Lists.newArrayList();
		for(int i = 0; i < 10; i++){
			String name = "ROLE_" + i;
			String code = "CODE_" + i;

			Role role = this.insertRole(name, code);
			allRoles.add(role);
		}
		
		List<Role> roles = roleMapper.findAll();
		
		assertTrue(allRoles.containsAll(roles));

	}
	
	@Test
	@Transactional
	public void testUpdate() throws Exception {
		User user = this.insertUser("makersoft-vertical", "makersoft-vertical", 18, User.SEX_MALE);

		assertNotNull("Insert User does not success!", user);
		
		user.setUsername("fengkuok");
		user.setPassword("password");
		int rows = userMapper.updateById(user);
		
		assertTrue("Update User does not success!", rows == 1);
		
		User fengkuok = userMapper.getById(user.getId());
		
		assertTrue("Update User does not success!", user.equals(fengkuok));
		
	}
	
	@Test
	@Transactional
	public void testDelete() throws Exception {
		User user = this.insertUser("makersoft-vertical", "makersoft-vertical", 18, User.SEX_MALE);
		assertNotNull("Insert User does not success!", user);
		
		int rows = userMapper.deleteById(user.getId());
		assertTrue("Delete User does not success!", rows == 1);
		
		user = userMapper.getById(user.getId());
		
		assertNull(user);
	}
	
	/**
	 * for insert new user 
	 */
	private User insertUser(String username, String password, int age,  int gender){
		User user = new User(username, password, gender);
		user.setAge(age);
		int rows = userMapper.insertUser(user);
		
		assertTrue("Insert User does not success!", rows == 1);
		
		assertNotNull(user.getId());
		
		return user;
	}
	
	/**
	 * for insert new user 
	 */
	private Role insertRole(String name, String code){
		Role role = new Role(name, code);
		int rows = roleMapper.insert(role);
		
		assertTrue("Insert Role does not success!", rows == 1);
		
		assertNotNull(role.getId());
		
		return role;
	}

}
