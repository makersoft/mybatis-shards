/*
 * @(#)ShardStrategyFactory.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy;

import java.util.List;

import org.makersoft.shards.ShardId;

/**
 * 策略
 */
public interface ShardStrategyFactory {

    /**
     * 创建一个新的策略.
     * 
     * @param shardIds
     * @return 
     */
	ShardStrategy newShardStrategy(List<ShardId> shardIds);
}
