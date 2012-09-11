/*
 * @(#)AbstractShardEntity.java 2012-9-4 下午23:59:06
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.domain;

import java.io.Serializable;

import org.makersoft.shards.annotation.PrimaryKey;
import org.makersoft.shards.annotation.ShardEntity;

@ShardEntity
public abstract class AbstractShardEntity implements Serializable{

	private static final long serialVersionUID = 5765444163885433060L;

	@PrimaryKey
	protected String id;

	public final String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
