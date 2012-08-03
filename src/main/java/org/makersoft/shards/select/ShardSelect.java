package org.makersoft.shards.select;

import java.util.List;
import java.util.Map;

/**
 * 
 */
public interface ShardSelect {

	<E> List<E> getResultList();
	
	<K, V> Map<K, V> getResultMap();
	
	<T> T getSingleResult(); 
}
