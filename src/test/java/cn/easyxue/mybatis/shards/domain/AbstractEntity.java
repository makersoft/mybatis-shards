/*
 * @(#)AbstractShardEntity.java 2012-9-4 下午23:59:06
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package cn.easyxue.mybatis.shards.domain;

import java.io.Serializable;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.ShardedEntity;

import org.makersoft.shards.annotation.PrimaryKey;

public abstract class AbstractEntity implements Serializable, ShardedEntity {

    private static final long serialVersionUID = 5765444163885433060L;

    @PrimaryKey
    protected String id;
    /**
     * 分区与分表信息.
     */
    protected ShardId shardId;

    public final String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractEntity other = (AbstractEntity) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * 分区与分表信息.
     * @return the shardId
     */
    public ShardId getShardId(){
        return shardId;
    };

    /**
     * 分区与分表信息.
     *
     * @param shardId the shardId to set
     */
    public void setShardId(ShardId shardId){
        this.shardId = shardId;
    };

}
