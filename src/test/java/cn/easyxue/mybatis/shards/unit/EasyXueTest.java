/*
 * @(#)BaseHasShardIdListTest.java 2013年8月30日 下午22:33:33
 *
 * Copyright (c) 2011-2013 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package cn.easyxue.mybatis.shards.unit;

import cn.easyxue.mybatis.shards.domain.shard0.Company;
import cn.easyxue.mybatis.shards.domain.shard0.User;
import cn.easyxue.mybatis.shards.domain.shard1.Employee;
import cn.easyxue.mybatis.shards.mapper.CompanyMapper;
import cn.easyxue.mybatis.shards.mapper.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;
import org.makersoft.shards.integration.BaseIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * 测试e企学的需求场景.
 * @author MaYichao
 */
@ContextConfiguration(locations = { "classpath:applicationContext-easyxue.xml" })
public class EasyXueTest extends BaseIntegrationTest{
    @Autowired
    UserMapper userMapper;
    
    @Autowired
    CompanyMapper companyMapper;
    
    @Test
    @Transactional
    public void testNewCompany(){
        //注解自动开启事务
        
        //注册一个新用户
        User toni = insertNewUser("托尼",User.SEX_MALE,"123456");
        assertNotNull("用户创建失败.", toni.getId());
        System.out.println("创建用户成功:"+toni.getId());
        
//        //注册一个新企业
        Company stark = insertCompany("Start工业",toni);
        assertNotNull("用户创建失败.", stark.getId());
        System.out.println("创建企业成功:"+stark.getId());
//        //创建企业分区表
        stark.build();
//        //添加第一个员工
//        Employee empToni = stark.addEmployee(toni);
    }


    /**
     * 创建一个基本用户.
     * @param name
     * @param sex
     * @param password
     * @return 
     */
    private User insertNewUser(String name, int sex, String password) {
        User user = new User(name, password, sex);
        int c = userMapper.insertUser(user);
        return user;
    }

    /**
     * 创建一个新的企业
     * @param name 企业名
     * @param creator 创建人
     * @return 
     */
    private Company insertCompany(String name, User creator) {
        Company comp = new Company(name, creator.getId());
        comp.setDbKey("shard_2");
        companyMapper.insertCompany(comp);
        return comp;
    }

}
