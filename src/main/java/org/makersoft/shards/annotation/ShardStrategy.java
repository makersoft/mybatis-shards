/*
 * @(#)Strategy.java 2012-9-10 下午4:05:52
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于指定策略.
 * 
 * @version 2012-9-10 下午4:05:52
 * @author Feng Kuok
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ShardStrategy {

	Class<?> mapper();
	
	StrategyType type() default StrategyType.None;
	
	public static enum StrategyType {
		None, Selection, Resolution, Reduce
	}
}
