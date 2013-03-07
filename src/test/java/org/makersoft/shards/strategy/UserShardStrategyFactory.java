/*
 * @(#)UserShardStrategyFactory.java 2012-9-12 下午12:57:18
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.RowBounds;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.domain.User;
import org.makersoft.shards.strategy.access.ShardAccessStrategy;
import org.makersoft.shards.strategy.access.impl.ParallelShardAccessStrategy;
import org.makersoft.shards.strategy.exit.impl.RowCountExitOperation;
import org.makersoft.shards.strategy.reduce.ShardReduceStrategy;
import org.makersoft.shards.strategy.resolution.ShardResolutionStrategy;
import org.makersoft.shards.strategy.resolution.ShardResolutionStrategyData;
import org.makersoft.shards.strategy.resolution.impl.AllShardsShardResolutionStrategy;
import org.makersoft.shards.strategy.selection.ShardSelectionStrategy;

/**
 * 
 */
public class UserShardStrategyFactory implements ShardStrategyFactory {

	@Override
	public ShardStrategy newShardStrategy(List<ShardId> shardIds) {
		ShardSelectionStrategy pss = this.getShardSelectionStrategy(shardIds);
		ShardResolutionStrategy prs = this.getShardResolutionStrategy(shardIds);
		ShardAccessStrategy pas = this.getShardAccessStrategy();
		ShardReduceStrategy srs = this.getShardReduceStrategy();
		return new ShardStrategyImpl(pss, prs, pas, srs);
	}
	
	private ShardSelectionStrategy getShardSelectionStrategy(final List<ShardId> shardIds){
		return new ShardSelectionStrategy(){

			@Override
			public ShardId selectShardIdForNewObject(String statement, Object obj) {
				if(obj instanceof User){
					User user = (User)obj;
					return this.determineShardId(user.getGender());
				}else {
					throw new IllegalArgumentException("a non-shardable object is being created"); 
				}
			}

			private ShardId determineShardId(int gender) {
				if(User.SEX_MALE == gender){
					return new ShardId(1);
				}else if(User.SEX_FEMALE == gender){
					return new ShardId(2);
				}
					
				return null;
			}

		};
	}
	
	private ShardResolutionStrategy getShardResolutionStrategy(final List<ShardId> shardIds){
//		return new AllShardsShardResolutionStrategy(shardIds);
		return new AllShardsShardResolutionStrategy(shardIds) {
			
			@Override
			public List<ShardId> selectShardIdsFromShardResolutionStrategyData(
					ShardResolutionStrategyData shardResolutionStrategyData) {
				String statement = shardResolutionStrategyData.getStatement();
				Object parameter = shardResolutionStrategyData.getParameter();
//				Serializable id = shardResolutionStrategyData.getId();
				
				//自定义规则...
				if(statement.endsWith("findByGender")){
					if(((Integer)parameter) == User.SEX_MALE){
						return Collections.singletonList(new ShardId(1));
					}else {
						return Collections.singletonList(new ShardId(2));
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
				if(statement.endsWith("getAllCount")){
					
					return new RowCountExitOperation().apply(values);
				}
				
				return values;
			}
		};
	}

}
