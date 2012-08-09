/*
 * @(#)BaseTest.java 2012-8-7 下午4:00:55
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.unit;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

/**
 * 单元测试基类.
 * 
 * @version 2012-8-7
 * @author Feng Kuok
 */
public abstract class BaseTest {
	
	public static final String APPLICATION_PROPERTIES = "application.properties";
	public static final String DS1_PROPERTIES = "databases/ds1.properties";
	public static final String DS2_PROPERTIES = "databases/ds2.properties";
	
	public static UnpooledDataSource createUnpooledDataSource(String resource)
			throws IOException {
		Properties props = Resources.getResourceAsProperties(resource);
		UnpooledDataSource ds = new UnpooledDataSource();
		ds.setDriver(props.getProperty("driver"));
		ds.setUrl(props.getProperty("url"));
		ds.setUsername(props.getProperty("username"));
		ds.setPassword(props.getProperty("password"));
		return ds;
	}

	public static PooledDataSource createPooledDataSource(String resource)
			throws IOException {
		Properties props = Resources.getResourceAsProperties(resource);
		PooledDataSource ds = new PooledDataSource();
		ds.setDriver(props.getProperty("driver"));
		ds.setUrl(props.getProperty("url"));
		ds.setUsername(props.getProperty("username"));
		ds.setPassword(props.getProperty("password"));
		return ds;
	}

	public static void runScript(DataSource ds, String resource)
			throws IOException, SQLException {
		Connection connection = ds.getConnection();
		try {
			ScriptRunner runner = new ScriptRunner(connection);
			runner.setAutoCommit(true);
			runner.setStopOnError(false);
			runner.setLogWriter(null);
			runner.setErrorLogWriter(null);
			runScript(runner, resource);
		} finally {
			connection.close();
		}
	}

	public static void runScript(ScriptRunner runner, String resource)
			throws IOException, SQLException {
		Reader reader = Resources.getResourceAsReader(resource);
		try {
			runner.runScript(reader);
		} finally {
			reader.close();
		}
	}
	
	public static DataSource createDs1DataSource() throws IOException,
			SQLException {
		DataSource ds = createUnpooledDataSource(DS1_PROPERTIES);
		return ds;
	}
	
	public static DataSource createDs2DataSource() throws IOException,
		SQLException {
		DataSource ds = createUnpooledDataSource(DS2_PROPERTIES);
		return ds;
	}
}
