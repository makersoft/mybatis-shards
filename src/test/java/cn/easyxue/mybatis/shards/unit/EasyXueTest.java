/*
 * @(#)BaseHasShardIdListTest.java 2013年8月30日 下午22:33:33
 *
 * Copyright (c) 2011-2013 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package cn.easyxue.mybatis.shards.unit;

import cn.easyxue.mybatis.shards.CompanyShardStrategyFactory;
import cn.easyxue.mybatis.shards.domain.shard0.Company;
import cn.easyxue.mybatis.shards.domain.shard0.User;
import cn.easyxue.mybatis.shards.domain.shard1.Employee;
import cn.easyxue.mybatis.shards.mapper.CompanyMapper;
import cn.easyxue.mybatis.shards.mapper.EmployeeMapper;
import cn.easyxue.mybatis.shards.mapper.UserMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;
import org.makersoft.shards.integration.BaseIntegrationTest;
import org.makersoft.shards.spring.ShardedSqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;

/**
 * 测试e企学的需求场景.
 *
 * @author MaYichao
 */
@ContextConfiguration(locations = {"classpath:applicationContext-easyxue.xml"})
public class EasyXueTest extends BaseIntegrationTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    CompanyMapper companyMapper;
    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    @Qualifier("jdbcTemplate_0")
    JdbcTemplate jdbc0;
    @Autowired
    @Qualifier("jdbcTemplate_1")
    JdbcTemplate jdbc1;

    @Autowired
    @Qualifier("jdbcTemplate_2")
    JdbcTemplate jdbc2;

    @Autowired
    @Qualifier("jdbcTemplate_3")
    JdbcTemplate jdbc3;

    @Autowired
    ShardedSqlSessionFactoryBean factoryBean;

    @Autowired
    CompanyShardStrategyFactory ssf;

    @Test
//    @Transactional
    public void testNewCompany() {
        //注册一个新用户
        User toni = insertNewUser("托尼", User.SEX_MALE, "123456");
        assertNotNull("用户创建失败.", toni.getId());
        System.out.println("创建用户成功:" + toni.getId());

        testNewCompany(101,toni);
        testNewCompany(102,toni);
        testNewCompany(103,toni);
    }

    @Transactional
    public void testNewCompany(int shardId,User creator) {

        //注解自动开启事务
//        //注册一个新企业
        Company stark = insertCompany("Start工业", creator,shardId);
        assertNotNull("用户创建失败.", stark.getId());
        System.out.println("创建企业成功:" + stark.getId());
//        //创建企业分区表
        buildCompany(stark);
        JdbcTemplate jdbc = getJdbcTemplate(Integer.parseInt(stark.getDbKey()));
        List<Employee> ems = jdbc.query("select * from t_" + stark.getId() + "_employee", new RowMapper<Employee>() {

            @Override
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                Employee em = new Employee(null, null);
                em.setComId(rs.getString("comId"));
                em.setId(rs.getString("id"));
                em.setUserId(rs.getString("userId"));
                return em;
            }

        });
        assertNotNull("创建企业表失败", ems);

//        //添加第一个员工
        Employee empToni = addEmployee(stark, creator);
        assertNotNull("员工创建失败.", empToni.getId());
        System.out.println("创建员工成功:" + empToni.getId());

    }

    /**
     * 创建一个基本用户.
     *
     * @param name
     * @param sex
     * @param password
     * @return
     */
    @Transactional
    private User insertNewUser(String name, int sex, String password) {
        User user = new User(name, password, sex);
        int c = userMapper.insertUser(user);
        return user;
    }

    /**
     * 创建一个新的企业
     *
     * @param name 企业名
     * @param creator 创建人
     * @return
     */
    @Transactional
    private Company insertCompany(String name, User creator,int shardId) {
        Company comp = new Company(name, creator.getId());

//        comp.setDbKey(String.valueOf(ssf.getDefaultShardId()));
        comp.setDbKey(String.valueOf(shardId));
        companyMapper.insertCompany(comp);
        return comp;
    }

    @Transactional
    private void buildCompany(Company stark) {
        //取得分区.
//        shardStrategyFactory.newShardStrategy(null)

//        Company c = new Company();
//        c.setId(stark.getId());
//        c.setDbKey(stark.getDbKey());
        Employee param = new Employee();
        param.setCompany(stark);

        employeeMapper.createTable(param);
    }

    @Transactional
    private Employee addEmployee(Company stark, User toni) {
        Employee employee = new Employee(stark, toni);
        employeeMapper.insert(employee);
        return employee;
    }

    private JdbcTemplate getJdbcTemplate(int dbKey) {
        switch (dbKey) {
            case 101:
                return jdbc1;
            case 102:
                return jdbc2;
            case 103:
                return jdbc3;
            default:
                return jdbc0;
        }
    }

}
