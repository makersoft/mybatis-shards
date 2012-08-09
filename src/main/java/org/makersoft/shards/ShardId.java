/*
 * @(#)ShardId.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards;

import org.makersoft.shards.utils.StringUtil;
import org.springframework.util.Assert;

/**
 * Uniquely identifies a virtual shard
 * 
 */
public class ShardId {
	
	private static final String SPLIT = "_";
//	private static final String EMPUTY_STRING = "";
	
	private final int shardId;
	
	private String prefix;
	
	private String suffix;
	
	//constractor
	public ShardId(int shardId) {
		this.shardId = shardId;
	}
	
	public final int getId() {
		return shardId;
	}

	public String getPrefix() {
		if(!StringUtil.isEmpty(prefix)){
			return prefix + SPLIT;
		}
		return prefix;
	}

	public void setPrefix(String prefix) {
		Assert.notNull(prefix,"can not set prefix with value null.");
		
		this.prefix = prefix;
	}

	public String getSuffix() {
		if(!StringUtil.isEmpty(suffix)){
			return SPLIT + suffix;
		}
		return suffix;
	}

	public void setSuffix(String suffix) {
		Assert.notNull(suffix,"can not set suffix with value null.");
		
		this.suffix = suffix;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + shardId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShardId other = (ShardId) obj;
		if (shardId != other.shardId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.valueOf(shardId);
	}


}
