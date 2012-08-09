/*
 * @(#)AvgResultsExitOperation.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.exit.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.makersoft.shards.strategy.exit.ExitOperation;
import org.makersoft.shards.strategy.exit.ExitOperationUtils;
import org.makersoft.shards.utils.Pair;

/**
 * 
 */
public class AvgResultsExitOperation implements ExitOperation {

	private final Log log = LogFactory.getLog(getClass());
	 
	public List<Object> apply(List<Object> results) {
		List<Object> nonNullResults = ExitOperationUtils.getNonNullList(results);
		Double total = null;
		int numResults = 0;
		for (Object result : nonNullResults) {
			/**
			 * We expect all entries to be Object arrays. the first entry in the
			 * array is the average (a double) the second entry in the array is
			 * the number of rows that were examined to arrive at the average.
			 */
			Pair<Double, Integer> pair = getResultPair(result);
			Double shardAvg = pair.first;
			if (shardAvg == null) {
				// if there's no result from this shard it doesn't go into the
				// calculation. This is consistent with how avg is implemented
				// in the database
				continue;
			}
			int shardResults = pair.second;
			Double shardTotal = shardAvg * shardResults;
			if (total == null) {
				total = shardTotal;
			} else {
				total += shardTotal;
			}
			numResults += shardResults;
		}
		if (numResults == 0 || total == null) {
			return Collections.singletonList(null);
		}
		return Collections.<Object>singletonList(total / numResults);
	}

	private Pair<Double, Integer> getResultPair(Object result) {
		if (!(result instanceof Object[])) {
			final String msg = "Wrong type in result list.  Expected "
					+ Object[].class + " but found " + result.getClass();
			log.error(msg);
			throw new IllegalStateException(msg);
		}
		Object[] resultArr = (Object[]) result;
		if (resultArr.length != 2) {
			final String msg = "Result array is wrong size.  Expected 2 "
					+ " but found " + resultArr.length;
			log.error(msg);
			throw new IllegalStateException(msg);
		}
		return Pair.of((Double) resultArr[0], (Integer) resultArr[1]);
	}

}
