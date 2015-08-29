/*
 * @(#)UserShardStrategy.java 2012-9-12 下午2:57:18
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.annotation.ShardStrategy;
import org.makersoft.shards.domain.shard0.User;
import org.makersoft.shards.mapper.shard0.UserMapper;
import org.makersoft.shards.strategy.selection.SelectionStrategy;

/**
 * Class description goes here.
 * 
 * @version 2012-9-12 下午2:57:18
 * @author Feng Kuok
 *  implements SelectionStrategy<User>, ResolutionStrategy<String>, ReduceStrategy
 */
@ShardStrategy(mapper = UserMapper.class)
public class UserShardStrategy implements SelectionStrategy<User>{
	
	public ShardId selectShardIdForNewObject(User entity) {
		if(User.SEX_MALE == entity.getGender()){
			return new ShardId(1);
		}else if(User.SEX_FEMALE == entity.getGender()){
			return new ShardId(2);
		}
		
		// 或指向写库
		
		return null;
	}

	public List<Object> reduce(String statement, Object parameter, RowBounds rowBounds,
			List<Object> inputValues) {
		return null;
	}

	public List<ShardId> selectShardIdsForResolution(String statement, Map<String, Object> parameter, String id) {
		return null;
	}

}
