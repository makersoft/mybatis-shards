package org.makersoft.shards.strategy.exit;

import java.util.List;

/**
 * 
 */
public interface ExitOperation {

	 List<Object> apply(List<Object> result);
}
