/*
 * @(#)Shard.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards;

import java.util.Collection;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 每个物理分区为一个Shard
 */
public interface Shard {

	SqlSessionFactory getSqlSessionFactory();

	/**
	 * @return establish a Session using the SessionFactoryImplementor
	 *         associated with this Shard and apply any OpenSessionEvents that
	 *         have been added. If the Session has already been established just
	 *         return it.
	 */
	SqlSession establishSqlSession();

	/**
	 * @return 与此分区相关联的{@link SqlSession}.
	 */
	@Deprecated
	SqlSession getSqlSession();

	/**
	 * @return 此 物理分区所映射的虚拟分区的ids.返回的Set是只读的. the ids of the virtual shards that
	 *         are mapped to this physical shard. The returned Set is
	 *         unmodifiable.
	 */
	Set<ShardId> getShardIds();
	
	Collection<String> getMappedStatementNames();
	
	boolean hasMapper(Class<?> type);
}
