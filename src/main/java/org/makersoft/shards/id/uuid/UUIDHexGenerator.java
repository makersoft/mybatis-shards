/*
 * @(#)UUIDHexGenerator.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.id.uuid;

import java.io.Serializable;

import org.apache.ibatis.session.SqlSession;

/**
 * 
 */
public class UUIDHexGenerator extends AbstractUUIDGenerator {
	private String sep = "/";

	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	public Serializable generate(SqlSession session, Object obj) {
		return new StringBuffer(36).append(format(getIP())).append(sep)
				.append(format(getJVM())).append(sep)
				.append(format(getHiTime())).append(sep)
				.append(format(getLoTime())).append(sep)
				.append(format(getCount())).toString();
	}

//	public void configure(Type type, Properties params, Dialect d) {
//		sep = PropertiesHelper.getString("separator", params, "");
//	}

//	public static void main(String[] args) throws Exception {
//		Properties props = new Properties();
//		props.setProperty("separator", "/");
//		IdentifierGenerator gen = new UUIDHexGenerator();
//		((Configurable) gen).configure(Hibernate.STRING, props, null);
//		IdentifierGenerator gen2 = new UUIDHexGenerator();
//		((Configurable) gen2).configure(Hibernate.STRING, props, null);
//
//		for (int i = 0; i < 10; i++) {
//			String id = (String) gen.generate(null, null);
//			System.out.println(id);
//			String id2 = (String) gen2.generate(null, null);
//			System.out.println(id2);
//		}
//
//	}

}
