/*
 * @(#)RoleMapper.java 2013-3-7 下午23:33:33
 *
 * Copyright (c) 2011-2013 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.mapper;

import java.util.List;

import org.makersoft.shards.annotation.MyBatisMapper;
import org.makersoft.shards.domain.Role;

/**
 * Role mapper for test.
 */
@MyBatisMapper
public interface RoleMapper {

	int insert(Role role);

	Role findById(String id);

	List<Role> findAll();

	Integer findAllCount();
	
	int updateById(Role role);
	
	int deleteById(String id);

	int deleteAll();

}
