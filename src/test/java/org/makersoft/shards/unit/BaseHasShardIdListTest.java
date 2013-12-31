/*
 * @(#)BaseHasShardIdListTest.java 2013年8月30日 下午22:33:33
 *
 * Copyright (c) 2011-2013 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.unit;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.makersoft.shards.BaseHasShardIdList;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.utils.Lists;

/**
 * @author Feng Kuok
 */
public class BaseHasShardIdListTest {

	@Test
	public void testShardIdListUnmodifiable() {
		List<ShardId> shardIdList = null;
		try {
			new MyBaseHasShardIdList(shardIdList);
			Assert.fail("expected npe");
		} catch (IllegalArgumentException npe) {
			// good
		}
		shardIdList = Lists.newArrayList();
		try {
			new MyBaseHasShardIdList(shardIdList);
			Assert.fail("expected iae");
		} catch (IllegalArgumentException iae) {
			// good
		}
		shardIdList.add(new ShardId(0));
		BaseHasShardIdList bhsil = new MyBaseHasShardIdList(shardIdList);
		ShardId anotherId = new ShardId(1);
		shardIdList.add(anotherId);
		// demonstrate that external changes to the list that was passed in
		// aren't reflected inside the object
		Assert.assertFalse(bhsil.getShardIds().contains(anotherId));
	}

	private static final class MyBaseHasShardIdList extends BaseHasShardIdList {

		protected MyBaseHasShardIdList(List<ShardId> shardIds) {
			super(shardIds);
		}
	}
}
