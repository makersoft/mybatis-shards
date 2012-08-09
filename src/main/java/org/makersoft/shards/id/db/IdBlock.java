/*
 * @(#)IdBlock.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.id.db;

/**
 * 
 */
public class IdBlock {
	long nextId;
	long lastId;

	public IdBlock(long nextId, long lastId) {
		this.nextId = nextId;
		this.lastId = lastId;
	}

	public long getNextId() {
		return nextId;
	}

	public long getLastId() {
		return lastId;
	}
}
