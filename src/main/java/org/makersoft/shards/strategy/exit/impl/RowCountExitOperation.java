/*
 * @(#)RowCountExitOperation.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.exit.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.makersoft.shards.strategy.exit.ExitOperation;
import org.makersoft.shards.strategy.exit.ExitOperationUtils;

/**
 * 
 */
public class RowCountExitOperation implements ExitOperation {

	@Override
	public List<Object> apply(List<Object> result) {
		List<Object> nonNullResults = ExitOperationUtils.getNonNullList(result);

		return Collections.<Object>singletonList(this.getCount(nonNullResults));
	}
	
	private Number getCount(List<Object> results) {
		BigDecimal sum = new BigDecimal(0.0);
		if(results != null){
			for (Object obj : results) {
				sum = sum.add(new BigDecimal(obj.toString()));
			}
		
			Object obj = results.get(0);
			if(obj instanceof Integer){
				return sum.intValue();
			}else if(obj instanceof Long){
				return sum.longValue();
			}
		}
		return sum;
	}

}
