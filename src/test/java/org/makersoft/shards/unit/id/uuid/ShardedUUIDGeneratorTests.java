/*
 * @(#)ShardedUUIDGeneratorTests.java 2012-8-22 下午2:00:03
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.unit.id.uuid;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.id.IdGenerator;
import org.makersoft.shards.id.uuid.ShardedUUIDGenerator;
import org.makersoft.shards.session.impl.ShardedSqlSessionImpl;

/**
 * UUID生成单元测试.
 * 
 * @version 2012-8-22 下午2:00:03
 * @author Feng Kuok
 */
public class ShardedUUIDGeneratorTests {

	public static final ShardId shardId = new ShardId(0);

	public IdGenerator idGenerator;

	@BeforeClass
	public static void init() {
		ShardedSqlSessionImpl.setCurrentSubgraphShardId(shardId);
	}

	@Before
	public void setup() {
		idGenerator = new ShardedUUIDGenerator();
	}

	@Test
	public void testGenerate() throws Exception {
		Assert.assertNotNull(idGenerator);

		Serializable id = idGenerator.generate(null, null);

		Assert.assertNotNull(id);
	}

	@Test
	public void testMultiThreadGenerate() throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors());
		final CopyOnWriteArrayList<Serializable> list = new CopyOnWriteArrayList<Serializable>();

		for (int i = 0; i < 10000; i++) {
			executorService.execute(new Runnable() {

				@Override
				public void run() {
					ShardedSqlSessionImpl.setCurrentSubgraphShardId(shardId);
					Serializable id = idGenerator.generate(null, null);
					list.addIfAbsent(id); // 不重复则添加
				}
			});
		}

		executorService.shutdown();
		while (!executorService.isTerminated()) {
			// wait for completed all task;
		}

		// 验证所有id不重复
		Assert.assertEquals(10000, list.size());

	}

	@Test
	public void testExtractShardId() throws Exception {
		Assert.assertNotNull(idGenerator);

		ShardId shardId = idGenerator.extractShardId(idGenerator.generate(null, null));

		Assert.assertEquals(ShardedUUIDGeneratorTests.shardId, shardId);
	}
}
