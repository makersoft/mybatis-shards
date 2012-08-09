/*
 * @(#)ExitOperationsCollector.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.exit;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Classes that implement this interface are designed to manage the results
 * of a incomplete execution of a query/critieria. For example, with averages
 * the result of each query/critieria should be a list objects on which to
 * calculate the average, rather than the avgerages on each shard. Or the
 * the sum of maxResults(200) should be the sum of only 200 results, not the
 * sum of the sums of 200 results per shard.
 */
public interface ExitOperationsCollector {

	List<Object> apply(List<Object> result);

	void setSqlSessionFactory(SqlSessionFactory sessionFactory);
}
