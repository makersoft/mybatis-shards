/*
 * @(#)ShardContext.java 2012-9-17 下午2:33:17
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.engine;

import java.util.Set;

/**
 * 上下文.
 * 
 * @version 2012-9-17 下午2:33:17
 * @author Feng Kuok
 */
public interface ShardContext {

	String getName();

	Object get(String key);

	<T> T get(Class<T> type);

	boolean has(String key);

	Object set(String key, Object value);

	Set<String> keys();
}
