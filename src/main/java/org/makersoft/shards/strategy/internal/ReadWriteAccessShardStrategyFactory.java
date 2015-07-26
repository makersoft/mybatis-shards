/*
 * @(#)ReadWriteAccessShardStrategyFactory.java 7/25/15 5:39 PM
 *
 * Copyright (c) 2011-2015 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package org.makersoft.shards.strategy.internal;

import org.apache.ibatis.session.RowBounds;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.strategy.ShardStrategy;
import org.makersoft.shards.strategy.ShardStrategyFactory;
import org.makersoft.shards.strategy.ShardStrategyImpl;
import org.makersoft.shards.strategy.access.ShardAccessStrategy;
import org.makersoft.shards.strategy.access.impl.SequentialShardAccessStrategy;
import org.makersoft.shards.strategy.reduce.ShardReduceStrategy;
import org.makersoft.shards.strategy.resolution.ShardResolutionStrategy;
import org.makersoft.shards.strategy.resolution.ShardResolutionStrategyData;
import org.makersoft.shards.strategy.resolution.impl.AllShardsShardResolutionStrategy;
import org.makersoft.shards.strategy.selection.ShardSelectionStrategy;

import java.util.List;

/**
 * Read write access shard strategy factory.
 */
public class ReadWriteAccessShardStrategyFactory implements ShardStrategyFactory {

    @Override
    public ShardStrategy newShardStrategy(List<ShardId> shardIds) {
        ShardSelectionStrategy pss = new ShardSelectionStrategy() {

            @Override
            public ShardId selectShardIdForNewObject(String statement, Object obj) {
                return null;
            }
        };


        ShardResolutionStrategy prs = new AllShardsShardResolutionStrategy(shardIds) {

            @Override
            public List<ShardId> selectShardIdsFromShardResolutionStrategyData(
                    ShardResolutionStrategyData shardResolutionStrategyData) {

                return null;
            }
        };


        ShardAccessStrategy pas = new SequentialShardAccessStrategy();

        ShardReduceStrategy srs = new ShardReduceStrategy() {

            @Override
            public List<Object> reduce(String statement, Object parameter,
                                       RowBounds rowBounds, List<Object> values) {
                return values;
            }
        };

        return new ShardStrategyImpl(pss, prs, pas, srs);
    }
}
