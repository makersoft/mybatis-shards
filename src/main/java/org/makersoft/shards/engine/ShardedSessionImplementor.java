/*
 * @(#)ShardedSessionImplementor.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.engine;

import java.util.List;

import org.makersoft.shards.Shard;

/**
 * 定义内部的<tt>ShardedSqlSession</tt>和其他分区的关联关系
 */
public interface ShardedSessionImplementor {

	/**
	 * 获取<tt>ShardedSqlSession</tt>所跨越的所有分区.
	 * 
	 * @return list of all shards the ShardedSqlSession is associated with
	 */
	List<Shard> getShards();
}
