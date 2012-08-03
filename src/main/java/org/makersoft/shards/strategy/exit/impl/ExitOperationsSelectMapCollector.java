package org.makersoft.shards.strategy.exit.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.makersoft.shards.strategy.exit.ExitOperationsCollector;

/**
 * 
 */
public class ExitOperationsSelectMapCollector implements ExitOperationsCollector {

	@Override
	public List<Object> apply(List<Object> result) {
		return null;
	}

	@Override
	public void setSqlSessionFactory(SqlSessionFactory sessionFactory) {
		
	}

}
