package org.makersoft.shards.strategy.exit.impl;

import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.makersoft.shards.strategy.exit.ExitOperation;
import org.makersoft.shards.strategy.exit.ExitOperationUtils;

/**
 * 
 */
public class RowBoundsExitOperation implements ExitOperation {
	
	private final RowBounds rowBounds;
	
	public RowBoundsExitOperation(RowBounds rowBounds){
		this.rowBounds = rowBounds;
	}

	@Override
	public List<Object> apply(List<Object> results) {
		List<Object> nonNullResults = ExitOperationUtils.getNonNullList(results);
		
	    if(nonNullResults.size() <= rowBounds.getOffset()) {
	      return Collections.emptyList();
	    }
	    
	    results = nonNullResults.subList(rowBounds.getOffset(), nonNullResults.size());
	    if(rowBounds.getLimit() < Integer.MAX_VALUE){
	    	results = nonNullResults.subList(0, Math.min(nonNullResults.size(), rowBounds.getLimit()));
	    }
	    
		return results;
	}

}
