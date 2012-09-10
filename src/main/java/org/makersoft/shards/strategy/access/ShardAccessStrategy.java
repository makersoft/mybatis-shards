/*
 * @(#)ShardAccessStrategy.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.access;

import java.util.List;

import org.makersoft.shards.Shard;
import org.makersoft.shards.ShardOperation;
import org.makersoft.shards.strategy.exit.ExitOperationsCollector;
import org.makersoft.shards.strategy.exit.ExitStrategy;


/**
 * 分区访问策略
 */
public interface ShardAccessStrategy {
	 <T> T apply(List<Shard> shards, ShardOperation<T> operation, ExitStrategy<T> exitStrategy, ExitOperationsCollector exitOperationsCollector);
}
