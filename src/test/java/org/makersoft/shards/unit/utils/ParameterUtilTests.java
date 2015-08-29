/*
 * @(#)ParameterUtilTests.java 2012-8-24 下午3:34:45
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.unit.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.domain.shard0.User;
import org.makersoft.shards.utils.ParameterUtil;

/**
 * unit test.
 * 
 * @version 2012-8-24 下午3:34:45
 * @author Feng Kuok
 */
public class ParameterUtilTests {

	private ShardId shardId;

	private static final String PREFIX = "prefix";
	private static final String SUFFIX = "suffix";

	private static final String PREFIX_VALUE = "prefix_";
	private static final String SUFFIX_VALUE = "_suffix";

	@Before
	public void setup() throws Exception {
		shardId = new ShardId(0);
		shardId.setPrefix("prefix");
		shardId.setSuffix("suffix");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testPrimitiveTypes() throws Exception {
		Object[] primitiveParams = new Object[] { "string", 1L, 10, true, 'c' };

		for (Object parameter : primitiveParams) {

			Object obj = ParameterUtil.resolve(parameter, shardId);

			Assert.assertTrue(obj instanceof Map);

			Map<String, Object> map = (Map<String, Object>) obj;
			Assert.assertEquals(PREFIX_VALUE, map.get(PREFIX));
			Assert.assertEquals(SUFFIX_VALUE, map.get(SUFFIX));

			// 对于任何key，调用map的containKey方法均返回true
			Assert.assertTrue(map.containsKey("any_key"));

			// 对于任何非prefix，suffix 的 key均返回原来传递的参数
			Assert.assertEquals(parameter, map.get("any_key"));
		}

	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMap() throws Exception {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", 1);
		parameter.put("name", "makersoft");

		Object obj = ParameterUtil.resolve(parameter, shardId);
		Assert.assertTrue(obj instanceof Map);

		Map<String, Object> map = (Map<String, Object>) obj;
		Assert.assertEquals(PREFIX_VALUE, map.get(PREFIX));
		Assert.assertEquals(SUFFIX_VALUE, map.get(SUFFIX));

		Assert.assertEquals(parameter.get("id"), map.get("id"));
		Assert.assertEquals(parameter.get("name"), map.get("name"));

	}

	@Test
	@SuppressWarnings("unchecked")
	public void testList() throws Exception {
		List<String> parameter = new ArrayList<String>();
		parameter.add("s1");
		parameter.add("s2");

		Object obj = ParameterUtil.resolve(parameter, shardId);
		Assert.assertTrue(obj instanceof Map);

		Map<String, Object> map = (Map<String, Object>) obj;
		Assert.assertEquals(PREFIX_VALUE, map.get(PREFIX));
		Assert.assertEquals(SUFFIX_VALUE, map.get(SUFFIX));

		Assert.assertEquals(parameter, map.get("list"));

	}

	@Test
	@SuppressWarnings("unchecked")
	public void testArray() throws Exception {
		String[] parameters = new String[] { "a1", "a2" };

		Object obj = ParameterUtil.resolve(parameters, shardId);
		Assert.assertTrue(obj instanceof Map);

		Map<String, Object> map = (Map<String, Object>) obj;
		Assert.assertEquals(PREFIX_VALUE, map.get(PREFIX));
		Assert.assertEquals(SUFFIX_VALUE, map.get(SUFFIX));

		Assert.assertEquals(parameters, map.get("array"));

	}

	@Test
	@SuppressWarnings("unchecked")
	public void testObject() throws Exception {
		User user = new User();
		user.setId("id");
		user.setUsername("makersoft");
		user.setPassword("password");
		user.setAge(20);

		Object obj = ParameterUtil.resolve(user, shardId);
		Assert.assertTrue(obj instanceof Map);

		Map<String, Object> map = (Map<String, Object>) obj;
		Assert.assertEquals(PREFIX_VALUE, map.get(PREFIX));
		Assert.assertEquals(SUFFIX_VALUE, map.get(SUFFIX));

		Assert.assertEquals(user.getId(), map.get("id"));
		Assert.assertEquals(user.getUsername(), map.get("username"));
		Assert.assertEquals(user.getPassword(), map.get("password"));
		Assert.assertEquals(user.getAge(), map.get("age"));
	}

	@Test
	public void testNull() throws Exception {
		Object obj = ParameterUtil.resolve(null, shardId);
		Assert.assertNull(obj);
	}

	@Test
	public void testExtractPrimaryKey() throws Exception {
		final String id = "id";
		User user = new User();
		user.setId(id);

		Serializable privaryKey = ParameterUtil.extractPrimaryKey(user);

		Assert.assertEquals(privaryKey, id);
	}

	@Test
	public void testGeneratePrimaryKey() throws Exception {
		final String id = "id";

		User user = (User) ParameterUtil.generatePrimaryKey(new User(), id);

		Assert.assertEquals(id, user.getId());
	}

}
