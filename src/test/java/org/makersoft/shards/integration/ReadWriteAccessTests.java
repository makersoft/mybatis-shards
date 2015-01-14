/*
 * @(#)ReadWriteAccessTests.java 2014年3月17日 下午23:33:33
 *
 * Copyright (c) 2011-2014 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.integration;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * Read write access integration text.
 */
@ContextConfiguration(locations = { "classpath:applicationContext-read-write-access.xml" })
public class ReadWriteAccessTests extends BaseIntegrationTest{

    @Test
    public void test_read(){
        Assert.assertTrue(true);
    }
}
