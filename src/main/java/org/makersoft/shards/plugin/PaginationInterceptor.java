/*
 * @(#)PaginationInterceptor.java 2012-8-17 下午2:32:29
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.plugin;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.makersoft.shards.MyBatisShardsException;
import org.makersoft.shards.plugin.spi.Dialect;

/**
 * 物理真分页组件.
 * <p>
 * 使用方法：
 * </p>
 * 
 * <code>
 * 	<plugins>
 *  	<plugin interceptor="org.makersoft.shards.plugin.PaginationInterceptor">
 *      	<property name="dialect" value="org.makersoft.shards.plugin.spi.impl.MySQLDialect"/>
 *  	</plugin>
 *  </plugins> 
 * </code>
 * 
 * @version 2012-8-17 下午2:32:29
 * @author Feng Kuok
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class,
		Object.class, RowBounds.class, ResultHandler.class }) })
public class PaginationInterceptor implements Interceptor {

	static int INDEX_MAPPED_STATEMENT = 0;
	static int INDEX_PARAMETER = 1;
	static int INDEX_ROW_BOUNDS = 2;
	static int INDEX_RESULT_HANDLER = 3;

	protected Dialect dialect;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		final MappedStatement mappedStatement = this.getMappedStatement(invocation);
		final Object parameter = this.getParameter(invocation);
		final RowBounds rowBounds = this.getRowBounds(invocation);

		final int offset = rowBounds.getOffset();
		final int limit = rowBounds.getLimit();

		if (dialect.supportLimit()
				&& (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
			BoundSql boundSql = mappedStatement.getBoundSql(parameter);
			String sql = boundSql.getSql().trim();

			if (dialect.supportOffsetLimit()) {
				sql = dialect.getLimitString(sql, offset, limit);
			} else {
				sql = dialect.getLimitString(sql, RowBounds.NO_ROW_OFFSET, limit);
			}

			this.setMappedStatement(invocation,
					this.buildMappedStatement(mappedStatement, boundSql, sql));
			this.setRowBounds(invocation, RowBounds.DEFAULT);
		}

		return invocation.proceed();
	}

	private MappedStatement buildMappedStatement(MappedStatement ms, BoundSql boundSql, String sql) {
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(),
				new BoundSqlSqlSource(ms, boundSql, sql), ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.fetchSize(ms.getFetchSize());
		builder.timeout(ms.getTimeout());
		builder.statementType(ms.getStatementType());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		builder.keyGenerator(ms.getKeyGenerator());
		builder.keyProperty(delimitedArraytoString(ms.getKeyProperties()));
		builder.keyColumn(delimitedArraytoString(ms.getKeyColumns()));
		builder.databaseId(ms.getDatabaseId());

		return builder.build();
	}

	private MappedStatement getMappedStatement(Invocation invocation) {
		return (MappedStatement) invocation.getArgs()[INDEX_MAPPED_STATEMENT];
	}

	private void setMappedStatement(Invocation invocation, MappedStatement mappedStatement) {
		invocation.getArgs()[INDEX_MAPPED_STATEMENT] = mappedStatement;
	}

	private Object getParameter(Invocation invocation) {
		return invocation.getArgs()[INDEX_PARAMETER];
	}

	private RowBounds getRowBounds(Invocation invocation) {
		return (RowBounds) invocation.getArgs()[INDEX_ROW_BOUNDS];
	}

	private void setRowBounds(Invocation invocation, RowBounds rowBounds) {
		invocation.getArgs()[INDEX_ROW_BOUNDS] = rowBounds;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		String dialectClass = properties.getProperty("dialect");
		try {
			dialect = (Dialect) Class.forName(dialectClass).newInstance();
		} catch (Exception e) {
			throw new MyBatisShardsException("Can not create dialect instance by dialect:"
					+ dialectClass, e);
		}
	}

	private static String delimitedArraytoString(String[] in) {
		if (in == null || in.length == 0) {
			return null;
		} else {
			StringBuffer answer = new StringBuffer();
			for (String str : in) {
				answer.append(str).append(",");
			}
			return answer.toString();
		}
	}

	public static class BoundSqlSqlSource implements SqlSource {
		private final BoundSql boundSql;

		public BoundSqlSqlSource(MappedStatement ms, BoundSql boundSql, String sql) {
			this.boundSql = buildBoundSql(ms, boundSql, sql);
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}

		private BoundSql buildBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
			BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql,
					boundSql.getParameterMappings(), boundSql.getParameterObject());
			for (ParameterMapping mapping : boundSql.getParameterMappings()) {
				String prop = mapping.getProperty();
				if (boundSql.hasAdditionalParameter(prop)) {
					newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
				}
			}
			return newBoundSql;
		}

	}
}
