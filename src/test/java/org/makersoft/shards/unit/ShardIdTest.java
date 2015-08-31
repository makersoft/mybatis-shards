/*
 * @(#)ShardIdTest.java 8/29/15 5:44 PM
 *
 * Copyright (c) 2011-2015 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package org.makersoft.shards.unit;

import org.junit.Test;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.utils.Lists;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * unit test for ShardId
 *
 * @see org.makersoft.shards.ShardId
 */
public class ShardIdTest {

    @Test(expected = IllegalArgumentException.class)
    public void testSetPrefixWithNull() {
        ShardId shardId_0 = new ShardId(0);
        shardId_0.setPrefix(null);
    }

    @Test
    public void testGetPrefix() {
        ShardId shardId_0 = new ShardId(0);
        shardId_0.setPrefix("T");

        assertEquals(shardId_0.getPrefix(), "T_");
    }

    @Test
    public void testGetSuffix() {
        ShardId shardId_0 = new ShardId(0);
        shardId_0.setSuffix("0");

        assertEquals(shardId_0.getSuffix(), "_0");
    }

    @Test
    public void testFindByShardId() {
        ShardId shardId_0 = new ShardId(0);
        ShardId shardId_1 = new ShardId(1);
        ShardId shardId_2 = new ShardId(2);

        List<ShardId> shardIds = Lists.newArrayList(shardId_0, shardId_1, shardId_2);

        ShardId shardId = ShardId.findByShardId(shardIds, 0);

        assertEquals(shardId.getId(), 0);
    }

    @Test
    public void testEquals() {
        ShardId shardId_a = new ShardId(0);
        ShardId shardId_b = new ShardId(0);

        assertTrue(shardId_a.equals(shardId_b));

    }

}
