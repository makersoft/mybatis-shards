package org.makersoft.shards.strategy.exit.impl;

import java.util.List;
import java.util.Set;

import org.makersoft.shards.strategy.exit.ExitOperation;
import org.makersoft.shards.strategy.exit.ExitOperationUtils;
import org.makersoft.shards.utils.Lists;
import org.makersoft.shards.utils.Sets;

/**
 * 
 */
public class DistinctExitOperation implements ExitOperation {

	@Override
	public List<Object> apply(List<Object> results) {
		Set<Object> uniqueSet = Sets.newHashSet();
	    uniqueSet.addAll(ExitOperationUtils.getNonNullList(results));

	    List<Object> uniqueList = Lists.newArrayList(uniqueSet);
	    
	    return uniqueList;
	}

	

}
