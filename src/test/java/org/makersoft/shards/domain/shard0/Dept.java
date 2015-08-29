/*
 * @(#)Dept.java 8/18/15 3:21 PM
 *
 * Copyright (c) 2011-2015 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package org.makersoft.shards.domain.shard0;

import java.io.Serializable;

/**
 * test Demo for auto increment id.
 */
public class Dept implements Serializable{

    private Long id;

    private String name;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
