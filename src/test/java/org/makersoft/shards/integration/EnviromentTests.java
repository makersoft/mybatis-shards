/*
 * @(#)EnviromentTests.java 2012-9-4 下午23:59:06
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.integration;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class EnviromentTests extends AbstractJUnit4SpringContextTests{

	@Test
	public void test_spring_context(){
		Assert.assertNotNull(super.applicationContext);
	}
	
}
