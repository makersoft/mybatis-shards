/*
 * @(#)UserShardStrategyFactory.java 2012-9-12 下午12:57:18
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package cn.easyxue.mybatis.shards;

import cn.easyxue.mybatis.shards.domain.shard1.CompanyEntity;
import org.makersoft.shards.strategy.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.domain.shard0.User;
import org.makersoft.shards.strategy.access.ShardAccessStrategy;
import org.makersoft.shards.strategy.access.impl.ParallelShardAccessStrategy;
import org.makersoft.shards.strategy.exit.impl.RowCountExitOperation;
import org.makersoft.shards.strategy.reduce.ShardReduceStrategy;
import org.makersoft.shards.strategy.resolution.ShardResolutionStrategy;
import org.makersoft.shards.strategy.resolution.ShardResolutionStrategyData;
import org.makersoft.shards.strategy.resolution.impl.AllShardsShardResolutionStrategy;
import org.makersoft.shards.strategy.selection.ShardSelectionStrategy;

/**
 * 根据企业水平切分策略.
 */
public class CompanyShardStrategyFactory implements ShardStrategyFactory {

    private static Logger log = Logger.getLogger(CompanyShardStrategyFactory.class);

    @Override
    public ShardStrategy newShardStrategy(List<ShardId> shardIds) {
        ShardSelectionStrategy pss = this.getShardSelectionStrategy(shardIds);
        ShardResolutionStrategy prs = this.getShardResolutionStrategy(shardIds);
        ShardAccessStrategy pas = this.getShardAccessStrategy();
        ShardReduceStrategy srs = this.getShardReduceStrategy();
        return new ShardStrategyImpl(pss, prs, pas, srs);
    }

    private ShardSelectionStrategy getShardSelectionStrategy(final List<ShardId> shardIds) {
        //每次请求都是一个新的策略对象,一定程度上可以防止内存洗泄露.
        //前提是这个方法应该要在连接数据库前被调用.
        return new ShardSelectionStrategy() {

            /**
             * 为新对象设定分区ID.
             *
             * @param statement
             * @param obj
             * @return
             */
            @Override
            public ShardId selectShardIdForNewObject(String statement, Object obj) {
                if (obj instanceof CompanyEntity) {
                    CompanyEntity comp = (CompanyEntity) obj;

                    ShardId si = this.determineShardId(comp.getCompany().getDbKey());
                    if (log.isDebugEnabled()) {
                        log.debug(String.format("Switch to db(%s) with param dbkey(%3$s) , sql(%2$s).", si.toString(), statement, comp.getCompany().getDbKey()));
                    }
                    comp.setShardId(si);
                    return si;
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug(String.format("Switch to default db with param sql(%1$s).",  statement));
                    }
                    return null;
                }
            }

            private ShardId determineShardId(String dbKey) {
                //根据dbKey对应到数据源.
                assert dbKey != null : "数据源怎么能是空的呢?";
                int id = Integer.parseInt(dbKey);
                for (ShardId shardId : shardIds) {
                    if (shardId.getId() == id) {
                        return shardId;
                    }
                }

                return null;
            }

        };
    }

    private ShardResolutionStrategy getShardResolutionStrategy(final List<ShardId> shardIds) {
        return new AllShardsShardResolutionStrategy(shardIds) {
            //这里已经没有数据源对象,数据源应该在此之前已经切换了.
            @Override
            public List<ShardId> selectShardIdsFromShardResolutionStrategyData(
                    ShardResolutionStrategyData shardResolutionStrategyData) {
                String statement = shardResolutionStrategyData.getStatement();
                Object parameter = shardResolutionStrategyData.getParameter();
//				Serializable id = shardResolutionStrategyData.getId();

                //自定义规则...
                if (statement.endsWith("findByGender")) {
                    if (((Integer) parameter) == User.SEX_MALE) {
                        return Collections.singletonList(ShardId.findByShardId(shardIds, 0));
                    } else {
                        return Collections.singletonList(ShardId.findByShardId(shardIds, 1));
                    }
                }

                return super.getShardIds();
            }
        };
    }

    private ShardAccessStrategy getShardAccessStrategy() {
        ThreadFactory factory = new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            }
        };

        ThreadPoolExecutor exec = new ThreadPoolExecutor(10, 50, 60,
                TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), factory);

        return new ParallelShardAccessStrategy(exec);

//		return new SequentialShardAccessStrategy();
    }

    private ShardReduceStrategy getShardReduceStrategy() {
        return new ShardReduceStrategy() {

            @Override
            public List<Object> reduce(String statement, Object parameter, RowBounds rowBounds,
                    List<Object> values) {
                if (statement.endsWith("getAllCount")) {

                    return new RowCountExitOperation().apply(values);
                }

                return values;
            }
        };
    }

}
