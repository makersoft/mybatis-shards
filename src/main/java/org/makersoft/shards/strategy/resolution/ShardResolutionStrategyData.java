package org.makersoft.shards.strategy.resolution;

import java.io.Serializable;

/**
 * 
 */
public interface ShardResolutionStrategyData {

	String getStatement(); 
	
	Object getParameter();

	Serializable getId();
}
