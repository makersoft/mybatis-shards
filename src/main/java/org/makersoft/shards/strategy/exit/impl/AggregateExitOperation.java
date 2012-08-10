/*
 * @(#)AggregateExitOperation.java 2012-8-1 下午10:00:00
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.makersoft.shards.strategy.exit.ExitOperation;
import org.makersoft.shards.strategy.exit.ExitOperationUtils;
import org.makersoft.shards.utils.Assert;
import org.makersoft.shards.utils.Lists;

/**
 * 
 */
public class AggregateExitOperation implements ExitOperation {

	private final SupportedAggregations aggregate;

	// private final String fieldName;

	private final Log log = LogFactory.getLog(getClass());

	private enum SupportedAggregations {

		SUM("sum"), MIN("min"), MAX("max");

		private final String aggregate;

		private SupportedAggregations(String s) {
			this.aggregate = s;
		}

		public String getAggregate() {
			return aggregate;
		}

	}

	public AggregateExitOperation(String statementSuffix) {
		Assert.notNull(statementSuffix);
		
		this.aggregate = SupportedAggregations.valueOf(statementSuffix.toUpperCase());
	}

	@Override
	public List<Object> apply(List<Object> result) {
		if (result.size() == 0){
			return Lists.newArrayList((Object)0);
		}
			
		String className = result.get(0).getClass().getName();
		
		List<Object> nonNullResults = ExitOperationUtils.getNonNullList(result);
		
		switch (aggregate) {
		case MAX:
			return Collections.<Object>singletonList(Collections.max(ExitOperationUtils.getComparableList(nonNullResults)) );
		case MIN:
			return Collections.<Object>singletonList(Collections.min(ExitOperationUtils.getComparableList(nonNullResults)));
		case SUM:
			return Collections.<Object>singletonList(getSum(nonNullResults,null).intValue());
		default:
			log.error("Aggregation Projection is unsupported: " + aggregate);
			throw new UnsupportedOperationException(
					"Aggregation Projection is unsupported: " + aggregate);
		}
	}

	private BigDecimal getSum(List<Object> results, String fieldName) {
		BigDecimal sum = new BigDecimal(0.0);
		for (Object obj : results) {
			Number	num = getNumber(obj, fieldName);
			sum = sum.add(new BigDecimal(num.toString()));
		}
		return sum;
	}

	private Number getNumber(Object obj, String fieldName) {
		if(fieldName == null || "".equals(fieldName)){
			return (Number)obj;
		}
		return (Number) ExitOperationUtils.getPropertyValue(obj, fieldName);
	}

}
