/*
 * @(#)MultiDataSourcesTransactionStatus.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.transaction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractTransactionStatus;

/**
 * 
 */
public class MultiDataSourcesTransactionStatus extends
		AbstractTransactionStatus {
	
	private Map<DataSource, TransactionStatus> dataSourceTransactionStatusMap = new ConcurrentHashMap<DataSource, TransactionStatus>();
	
	@Override
	public boolean isNewTransaction() {
		return true;
	}
	
	/**
	 * 设置
	 * @param dataSource
	 * @param transactionStatus
	 * @return
	 */
	public Map<DataSource, TransactionStatus> put(DataSource dataSource, TransactionStatus transactionStatus){
		dataSourceTransactionStatusMap.put(dataSource, transactionStatus);
		return dataSourceTransactionStatusMap;
	}
	
	public TransactionStatus get(DataSource dataSource) {
		return dataSourceTransactionStatusMap.get(dataSource);
	}

}
