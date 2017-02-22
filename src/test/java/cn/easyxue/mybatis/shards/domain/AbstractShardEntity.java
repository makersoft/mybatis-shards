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
import org.makersoft.shards.ShardedEntity;

import org.makersoft.shards.annotation.ShardEntity;

@ShardEntity
public abstract class AbstractShardEntity extends AbstractEntity implements Serializable,ShardedEntity {

}
