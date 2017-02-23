/*
 * @(#)CompanyMapper.java 2012-9-4 下午3:37:09
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package cn.easyxue.mybatis.shards.mapper;

import java.util.List;

import org.makersoft.shards.annotation.MyBatisMapper;
import cn.easyxue.mybatis.shards.domain.shard0.Company;

/**
 * Company mapper for test.
 */
@MyBatisMapper
public interface CompanyMapper {

    Company getById(String id);

    List<Company> findAll();

//    List<Company> findByGender(int gender);

    int getAllCount();

    int insertCompany(Company user);

    int deleteAll();

    int deleteById(String id);

//    int udpateCompany(Company user);

    int updateById(Company user);

}
