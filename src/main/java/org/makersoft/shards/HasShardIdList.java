/*
 * @(#)HasShardIdList.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards;

import java.util.List;

/**
 * Interface for objects that can provide a List of ShardIds.
 */
public interface HasShardIdList {
	/**
	 * @return an unmodifiable list of {@link ShardId}s
	 */
	List<ShardId> getShardIds();
}
