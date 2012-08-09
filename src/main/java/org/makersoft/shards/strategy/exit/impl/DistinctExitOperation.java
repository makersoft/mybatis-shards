/*
 * @(#)DistinctExitOperation.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.exit.impl;

import java.util.List;
import java.util.Set;

import org.makersoft.shards.strategy.exit.ExitOperation;
import org.makersoft.shards.strategy.exit.ExitOperationUtils;
import org.makersoft.shards.utils.Lists;
import org.makersoft.shards.utils.Sets;

/**
 * 
 */
public class DistinctExitOperation implements ExitOperation {

	@Override
	public List<Object> apply(List<Object> results) {
		Set<Object> uniqueSet = Sets.newHashSet();
	    uniqueSet.addAll(ExitOperationUtils.getNonNullList(results));

	    List<Object> uniqueList = Lists.newArrayList(uniqueSet);
	    
	    return uniqueList;
	}

	

}
