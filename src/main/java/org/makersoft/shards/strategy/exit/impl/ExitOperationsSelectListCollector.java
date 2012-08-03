package org.makersoft.shards.strategy.exit.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.makersoft.shards.strategy.exit.ExitOperationsCollector;

/**
 * 
 */
public class ExitOperationsSelectListCollector implements ExitOperationsCollector {

	private final RowBounds rowBounds;

	public ExitOperationsSelectListCollector(RowBounds rowBounds){
		this.rowBounds = rowBounds;
	}
	
	@Override
	public List<Object> apply(List<Object> result) {
		if(rowBounds != null && rowBounds != RowBounds.DEFAULT){
			result = new RowBoundsExitOperation(rowBounds).apply(result);
		}

		return result;
	}

	@Override
	public void setSqlSessionFactory(SqlSessionFactory sessionFactory) {
		throw new UnsupportedOperationException();
	}

//	public ExitOperationsCollector setRowBounds(RowBounds rowBounds) {
//		this.rowBounds = rowBounds;
//		return this;
//	}

}
