/*
 * @(#)Role.java 2012-9-4 下午23:59:06
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package cn.easyxue.mybatis.shards.domain.shard0;

import cn.easyxue.mybatis.shards.domain.AbstractShardEntity;

/**
 * entity role for test.
 */
public class Company extends AbstractShardEntity {
	private static final long serialVersionUID = 2960427786748550511L;

	private String name;
	private String creatorId;
    /**
     * 数据分区名.
     */
    private String dbKey;
    
    
	public Company() {
	}

	public Company(String name, String code) {
		this.name = name;
		this.creatorId = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

    /**
     * 数据分区名.
     * @return the dbKey
     */
    public String getDbKey() {
        return dbKey;
    }

    /**
     * 数据分区名.
     * @param dbKey the dbKey to set
     */
    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    /**
     * 创建企业表.
     */
    public void build() {
        //员工表.
        
    }

}
