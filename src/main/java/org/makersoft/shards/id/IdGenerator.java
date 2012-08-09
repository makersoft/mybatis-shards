/*
 * @(#)IdGenerator.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.id;

import java.io.Serializable;

import org.apache.ibatis.session.SqlSession;
import org.makersoft.shards.ShardId;

/**
 * 主键生成器
 */
public interface IdGenerator {

	/**
	 * 生成主键
	 * @param session	当前的{@link SqlSession}
	 * @param object	需要生成主键的对象
	 * @return	生成的主键
	 */
	Serializable generate(SqlSession session, Object object);

	/**
	 * 根据主键提取出逻辑分区
	 * @param identifier	主键
	 * @return	逻辑分区 {@link ShardId}
	 */
	ShardId extractShardId(Serializable identifier);

}
