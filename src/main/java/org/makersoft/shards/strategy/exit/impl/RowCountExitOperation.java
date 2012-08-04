package org.makersoft.shards.strategy.exit.impl;

import java.math.BigDecimal;
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

		return Collections.<Object>singletonList(this.getCount(nonNullResults));
	}
	
	private Number getCount(List<Object> results) {
		BigDecimal sum = new BigDecimal(0.0);
		for (Object obj : results) {
			sum = sum.add(new BigDecimal(obj.toString()));
		}
		
		if(results != null){
			Object obj = results.get(0);
			if(obj instanceof Integer){
				return sum.intValue();
			}else if(obj instanceof Long){
				return sum.longValue();
			}
		}
		return sum;
	}

}
