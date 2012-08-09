/*
 * @(#)ExitOperation.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.exit;

import java.util.List;

/**
 * 
 */
public interface ExitOperation {

	 List<Object> apply(List<Object> result);
}
