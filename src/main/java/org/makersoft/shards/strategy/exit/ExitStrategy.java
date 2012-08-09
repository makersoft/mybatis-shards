/*
 * @(#)ExitStrategy.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.exit;

import org.makersoft.shards.Shard;

/**
 * 
 */
public interface ExitStrategy<T> {

	/**
	 * Add the provided result and return whether or not the caller can halt
	 * processing.
	 * 
	 * @param result
	 *            The result to add
	 * @return Whether or not the caller can halt processing
	 */
	boolean addResult(T result, Shard shard);

	T compileResults(ExitOperationsCollector exitOperationsCollector);

}
