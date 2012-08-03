package org.makersoft.shards.domain;

import java.io.Serializable;

import org.makersoft.shards.annotation.PrimaryKey;

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
