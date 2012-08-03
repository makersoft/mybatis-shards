package org.makersoft.shards.strategy.exit.impl;

import java.util.Collections;
import java.util.List;

import org.makersoft.shards.strategy.exit.ExitOperation;
import org.makersoft.shards.strategy.exit.ExitOperationUtils;

/**
 * 
 */
public class RowCountExitOperation implements ExitOperation {

	@Override
	public List<Object> apply(List<Object> result) {
		List<Object> nonNullResults = ExitOperationUtils.getNonNullList(result);

		return Collections.<Object>singletonList(nonNullResults.size());
	}

}
