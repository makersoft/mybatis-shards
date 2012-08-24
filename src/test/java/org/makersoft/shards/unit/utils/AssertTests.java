/*
 * @(#)AssertTests.java 2012-8-22 下午4:26:57
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.unit.utils;

import org.junit.Test;
import org.makersoft.shards.utils.Assert;

/**
 * 断言类单元测试.
 * 
 * @author Feng Kuok
 */
public class AssertTests {
	
	@Test(expected = IllegalArgumentException.class)
	public void testIsTrue() throws Exception{
		Assert.isTrue(false);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNotNull() throws Exception{
		Assert.notNull(null);
	}
}
