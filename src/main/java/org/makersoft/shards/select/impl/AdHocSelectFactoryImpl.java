package org.makersoft.shards.select.impl;

import org.apache.ibatis.session.RowBounds;
import org.makersoft.shards.select.SelectFactory;

/**
 * 
 */
public class AdHocSelectFactoryImpl implements SelectFactory {

	private final String statement;
	private final Object parameter;
	private final RowBounds rowBounds;

	public AdHocSelectFactoryImpl(String statement, Object parameter,
			RowBounds rowBounds) {
		this.statement = statement;
		this.parameter = parameter;
		this.rowBounds = rowBounds;

	}

	@Override
	public String getStatement() {
		return statement;
	}

	@Override
	public Object getParameter() {
		return parameter;
	}

	@Override
	public RowBounds getRowBounds() {
		return rowBounds;
	}

}
