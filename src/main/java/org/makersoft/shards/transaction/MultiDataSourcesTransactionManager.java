/*
 * @(#)MultiDataSourcesTransactionManager.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.makersoft.shards.utils.Assert;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class MultiDataSourcesTransactionManager implements PlatformTransactionManager, InitializingBean {
	
	private static final Logger logger = Logger.getLogger(MultiDataSourcesTransactionManager.class);

	/* 数据源 */
	private List<DataSource> dataSources;
	
	private Map<DataSource, DataSourceTransactionManager> transactionManagers = new HashMap<DataSource, DataSourceTransactionManager>();

	/**
	 * 统计提交
	 */
	private AtomicInteger commitCount = new AtomicInteger(0);
	
	/**
	 * 统计回滚
	 */
	private AtomicInteger rollbackCount  = new AtomicInteger(0);

	
	public void setDataSources(final List<DataSource> dataSources) {
		this.dataSources = dataSources;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(dataSources, "data source can not be null.");
		
		for (DataSource dataSource : dataSources) {
			DataSourceTransactionManager txManager = new DataSourceTransactionManager(dataSource);
			transactionManagers.put(dataSource, txManager);
		}
		this.addShutdownHook();
	}

	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				// rollback 和 commit如果不为0
				while (commitCount.get() != 0) {
					logger.info("Waiting for commit transaction.");
					try {
						Thread.currentThread().sleep(1);
					} catch (InterruptedException e) {
						logger.warn("interrupted when shuting down the query executor:\n{}",e);
					}
				}
				while (rollbackCount.get() != 0) {
					logger.info("Waiting for rollback transaction.");
					try {
						Thread.currentThread().sleep(1);
					} catch (InterruptedException e) {
						logger.warn("interrupted when shuting down the query executor:\n{}",e);
					}
				}
				logger.info("Transaction success.");
			}
		});

	}

	@Override
	public TransactionStatus getTransaction(TransactionDefinition definition)
			throws TransactionException {
		
		MultiDataSourcesTransactionStatus transactionStatus = new MultiDataSourcesTransactionStatus();
		
		for (DataSource dataSource : dataSources) {
			logger.info(dataSource + "正在开启事务...");
			
			DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition(definition);
			defaultTransactionDefinition.setName(definition.getName());

			PlatformTransactionManager txManager = this.transactionManagers.get(dataSource);
			TransactionStatus status = txManager.getTransaction(defaultTransactionDefinition);

			TransactionSynchronizationManager.setCurrentTransactionName(defaultTransactionDefinition.getName());
			
			transactionStatus.put(dataSource, status);
		}
		
		
		return transactionStatus;

	}

	
	@Override
	public void commit(TransactionStatus status) throws TransactionException {

		Throwable ex = null;
//		Collections.reverse(dataSources);
		for (int i = dataSources.size() - 1; i >= 0; i--) {
			DataSource dataSource = dataSources.get(i);
			try {
				commitCount.addAndGet(1);

				logger.debug("Committing JDBC transaction");
				
				DataSourceTransactionManager txManager = this.transactionManagers.get(dataSource);
				
				TransactionStatus transactionStatus = ((MultiDataSourcesTransactionStatus)status).get(dataSource);
				txManager.commit(transactionStatus);

				logger.debug("Commit JDBC transaction success");
			} catch (Throwable e) {
				logger.debug("Could not commit JDBC transaction", e);
				ex = e;
			} finally {
				commitCount.addAndGet(-1);
			}
		}
		
		if (ex != null) {
			throw new RuntimeException(ex);
		}

	}
	
	@Override
	public void rollback(TransactionStatus status) throws TransactionException {
		
		Throwable ex = null;

		//Cannot deactivate transaction synchronization - not active
//		Collections.reverse(dataSources);
		
		for (int i = dataSources.size() - 1; i >= 0; i--) {
			DataSource dataSource = dataSources.get(i);
			try {
				logger.debug("Rolling back JDBC transaction");
				
				rollbackCount.addAndGet(1);
				
				DataSourceTransactionManager txManager = this.transactionManagers.get(dataSource);
				TransactionStatus currentStatus = ((MultiDataSourcesTransactionStatus)status).get(dataSource);

				txManager.rollback(currentStatus);

				logger.info("Roll back JDBC transaction success");
			} catch (Throwable e) {
				logger.info("Could not roll back JDBC transaction", e);
				ex = e;
			} finally {
				rollbackCount.addAndGet(-1);
			}
		}
		
		if (ex != null) {
			throw new RuntimeException(ex);
		}
		
	}
	
}
