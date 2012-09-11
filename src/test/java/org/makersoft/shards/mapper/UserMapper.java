/*
 * @(#)UserMapper.java 2012-9-4 下午3:37:09
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.mapper;

import java.util.List;

import org.makersoft.shards.annotation.MyBatisMapper;
import org.makersoft.shards.domain.User;

/**
 * User mapper for test.
 */
@MyBatisMapper
public interface UserMapper {
	
	int insertUser(User user);

	User getById(String id);

	List<User> findAll();

	List<User> findByGender(int gender);

	int deleteAll();

	int deleteById(String id);

	int udpateUser(User user);

	int updateById(User user);

	int getAllCount();
}
