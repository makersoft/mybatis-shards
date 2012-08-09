/*
 * @(#)ShardIdResolver.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.session;

import java.util.List;

import org.makersoft.shards.Shard;
import org.makersoft.shards.ShardId;

/**
 * Interface for statement that are able to resolve shard of statements.
 */
public interface ShardIdResolver {

	 /**
	   * Gets ShardId of the shard given object lives on. Only consideres given
	   * Shards.
	   *
	   * @param obj Object whose Shard should be resolved
	   * @param shardsToConsider Shards which should be considered during resolution
	   * @return ShardId of the shard the object lives on; null if shard could not be resolved
	   */
	  /*@Nullable*/ ShardId getShardIdForStatementOrParameter(String statement, Object parameter, List<Shard> shardsToConsider);

	  /**
	   * Gets ShardId of the shard given object lives on.
	   *
	   * @param obj Object whose Shard should be resolved
	   * @return ShardId of the shard the object lives on; null if shard could not be resolved
	   */
	  /*@Nullable*/ ShardId getShardIdForStatementOrParameter(String statement, Object parameter);
}
