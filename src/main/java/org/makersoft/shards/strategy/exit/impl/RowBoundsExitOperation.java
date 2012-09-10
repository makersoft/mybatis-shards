/*
 * @(#)RowBoundsExitOperation.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.exit.impl;

import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.makersoft.shards.strategy.exit.ExitOperation;

/**
 * 
 */
public class RowBoundsExitOperation implements ExitOperation {
	
	private final RowBounds rowBounds;
	
	public RowBoundsExitOperation(RowBounds rowBounds){
		this.rowBounds = rowBounds;
	}

	@Override
	public List<Object> apply(List<Object> results) {
	    if(results.size() <= rowBounds.getOffset()) {
	      return Collections.emptyList();
	    }
	    
	    results = results.subList(rowBounds.getOffset(), results.size());
	    if(rowBounds.getLimit() < Integer.MAX_VALUE){
	    	results = results.subList(0, Math.min(results.size(), rowBounds.getLimit()));
	    }
	    
		return results;
	}

}
