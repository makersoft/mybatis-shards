/*
 * @(#)BaseHasShardIdList.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.makersoft.shards.utils.Assert;

/**
 * Base implementation for HasShadIdList. Takes care of null/empty checks.
 */
public abstract class BaseHasShardIdList implements HasShardIdList{
	// our list of {@link ShardId} objects
	protected final List<ShardId> shardIds;

	/**
	 * Construct a BaseHasShardIdList. {@link List} cannot be empty
	 * 
	 * @param shardIds
	 *            the {@link ShardId}s
	 */
	protected BaseHasShardIdList(List<ShardId> shardIds) {
		Assert.notNull(shardIds);
		Assert.notNull(!shardIds.isEmpty());
		// make our own copy to be safe
		this.shardIds = new ArrayList<ShardId>(shardIds);
	}

	@Override
	public List<ShardId> getShardIds() {
		return Collections.unmodifiableList(shardIds);
	}
}
