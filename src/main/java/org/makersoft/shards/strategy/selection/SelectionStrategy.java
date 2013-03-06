/*
 * @(#)SelectionStrategy.java 2012-9-12 下午3:28:19
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.selection;

import org.makersoft.shards.ShardId;

/**
 * 创建新对象时的，分区筛选策略
 */
public interface SelectionStrategy<T> {

	ShardId selectShardIdForNewObject(T entity);
	
}
