/*
 * @(#)DeptMapper.java 8/18/15 3:24 PM
 *
 * Copyright (c) 2011-2015 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package org.makersoft.shards.mapper.shard0;

import org.makersoft.shards.annotation.MyBatisMapper;
import org.makersoft.shards.domain.shard0.Dept;

/**
 * Dept mapper for test.
 */
@MyBatisMapper
public interface DeptMapper {

    int insert(Dept dept);
}
