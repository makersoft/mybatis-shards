/*
 * @(#)MyBatisMapper.java 2012-9-4 下午3:24:17
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
 * 标识MyBatis的Mapper,方便{@link org.mybatis.spring.mapper.MapperScannerConfigurer}的扫描.
 * 
 * @version 2012-9-4 下午3:24:17
 * @author Feng Kuok
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyBatisMapper {

}
