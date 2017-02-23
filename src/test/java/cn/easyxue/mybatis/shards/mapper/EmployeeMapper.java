/*
 * @(#)EmployeeMapper.java 2013-3-7 下午23:33:33
 *
 * Copyright (c) 2011-2013 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package cn.easyxue.mybatis.shards.mapper;

import cn.easyxue.mybatis.shards.domain.shard1.Employee;

import org.makersoft.shards.annotation.MyBatisMapper;

/**
 * Role mapper for test.
 */
@MyBatisMapper
public interface EmployeeMapper {

    int createTable(Employee employee);

    int insert(Employee employee);

//	Role findById(String id);
//
//	List<Role> findAll();
//
//	Integer findAllCount();
//	
//	int updateById(Role role);
//	
//	int deleteById(String id);
//
//	int deleteAll();
}
