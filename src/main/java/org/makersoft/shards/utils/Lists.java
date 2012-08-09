/*
 * @(#)Lists.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Helper methods related to {@link List}s.
 * 
 * @author maxr@google.com (Max Ross)
 */
public class Lists {

	private Lists() {
	}

	/**
	 * Construct a new {@link ArrayList}, taking advantage of type inference to
	 * avoid specifying the type on the rhs.
	 */
	public static <E> ArrayList<E> newArrayList() {
		return new ArrayList<E>();
	}

	/**
	 * Construct a new {@link ArrayList} with the specified capacity, taking
	 * advantage of type inference to avoid specifying the type on the rhs.
	 */
	public static <E> ArrayList<E> newArrayListWithCapacity(int initialCapacity) {
		return new ArrayList<E>(initialCapacity);
	}

	/**
	 * Construct a new {@link ArrayList} with the provided elements, taking
	 * advantage of type inference to avoid specifying the type on the rhs.
	 */
	public static <E> ArrayList<E> newArrayList(E... elements) {
		ArrayList<E> set = newArrayList();
		Collections.addAll(set, elements);
		return set;
	}

	/**
	 * Construct a new {@link ArrayList} with the contents of the provided
	 * {@link Iterable}, taking advantage of type inference to avoid specifying
	 * the type on the rhs.
	 */
	public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
		ArrayList<E> list = newArrayList();
		for (E e : elements) {
			list.add(e);
		}
		return list;
	}

	/**
	 * Construct a new {@link LinkedList}, taking advantage of type inference to
	 * avoid specifying the type on the rhs.
	 */
	public static <E> LinkedList<E> newLinkedList() {
		return new LinkedList<E>();
	}
}
