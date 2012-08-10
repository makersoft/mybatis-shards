/*
 * @(#)ParameterUtil.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.beanutils.PropertyUtils;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.annotation.PrimaryKey;

/**
 * 参数处理类
 */
public abstract class ParameterUtil {

	private ParameterUtil() {

	}

	/**
	 * 参数解析
	 * 
	 * @param obj
	 * @param shardId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object resolve(final Object obj, final ShardId shardId) {
		try {
			// 基本类型
			if (obj instanceof String || obj instanceof Number
					|| obj instanceof Boolean || obj instanceof Character) {
				Enhancer enhancer = new Enhancer();
				enhancer.setSuperclass(HashMap.class);
				enhancer.setCallback(new MethodInterceptor() {

					private final String prefix = shardId.getPrefix();
					private final String suffix = shardId.getSuffix();
					private final Object parameter = obj;

					@Override
					public Object intercept(Object object, Method method,
							Object[] args, MethodProxy proxy) throws Throwable {

						if("containsKey".equals(method.getName())){
							//对所有关于Map中containsKey的调用均返回TRUE
							return true;
						} 
							
						if (args.length > 0 && "get".equals(method.getName())) {
							if ("prefix".equals(args[0])) {
								return prefix;
							} else if ("suffix".equals(args[0])) {
								return suffix;
							} else {
								return parameter;
							}
						}

						return proxy.invokeSuper(object, args);
					}
				});

				return (HashMap) enhancer.create();
			} else if (obj instanceof Map) {
				Map parameter = (Map) obj;
				parameter.put("prefix", shardId.getPrefix());
				parameter.put("suffix", shardId.getSuffix());

				return parameter;
			} else if (obj instanceof List) {
				Map<String, Object> parameter = Maps.newHashMap();
				parameter.put("list", obj);
				parameter.put("prefix", shardId.getPrefix());
				parameter.put("suffix", shardId.getSuffix());

				return parameter;
			} else if (obj != null && obj.getClass().isArray()) {
				Map<String, Object> parameter = Maps.newHashMap();
				parameter.put("array", obj);
				parameter.put("prefix", shardId.getPrefix());
				parameter.put("suffix", shardId.getSuffix());

			} else if (obj instanceof Object) {
				Map<String, Object> parameter = PropertyUtils.describe(obj);
				parameter.put("prefix", shardId.getPrefix());
				parameter.put("suffix", shardId.getSuffix());

				return parameter;
			} else if (obj != null) {
				throw new UnsupportedOperationException(String.format(
						"The parameter of type {%s} is not supported.",
						obj.getClass()));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return null;
	}

	public static Serializable extractPrimaryKey(Object object) {
		if (object != null) {
			Class<?> clazz = object.getClass();
			Field[] first = clazz.getDeclaredFields();
			Field[] second = clazz.getSuperclass().getDeclaredFields();
			
			Field[] fields = Arrays.copyOf(first, first.length + second.length);
			System.arraycopy(second, 0, fields, first.length, second.length);

			for (Field field : fields) {
				field.setAccessible(true);
				
				PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
				if (primaryKey != null) {
					try {
						//去除0的情况
						Object result = field.get(object);
						if(result != null && "0".equals(result.toString())){
							return null;
						}
						return (Serializable) result;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}

		return null;
	}
	
	public static Object generatePrimaryKey(Object object, Serializable id) {
		if (object != null) {
			Assert.notNull(id, "generated id can not be null.");
			
			Class<?> clazz = object.getClass();
			Field[] first = clazz.getDeclaredFields();
			Field[] second = clazz.getSuperclass().getDeclaredFields();
			
			Field[] fields = Arrays.copyOf(first, first.length + second.length);
			System.arraycopy(second, 0, fields, first.length, second.length);

			for (Field field : fields) {
				field.setAccessible(true);
				
				PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
				if (primaryKey != null) {
					try {
						//set id
						field.set(object, id);
						
						return object;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}

		return null;
	}
	
	
	public static boolean isNumberic(Type type){
		String typeName = type.toString();
		if ("long".equalsIgnoreCase(typeName)
				|| "java.lang.Long".equals(typeName)
				|| "int".equalsIgnoreCase(typeName)
				|| "java.lang.Integer".equals(typeName)) {
			
			return true;
		}
		
		return false;
	}
	
	public static boolean isPrimitiveParameter(Object obj){
		if(obj instanceof String || obj instanceof Number){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
//		Test test = new Test();
//		test.setId(111L);
//		test.setName("test");
//		System.out.println(extractPrimaryKey(test));
		long i = 0;
		Long j = 1L;
		int k = 2;
		Integer m = 3;
		String string = "ssss";
		System.out.println(isPrimitiveParameter(i));
		System.out.println(isPrimitiveParameter(j));
		System.out.println(isPrimitiveParameter(k));
		System.out.println(isPrimitiveParameter(m));
		System.out.println(isPrimitiveParameter(string));
	}
	
	static class Test{
		@PrimaryKey
		private Long id;
		private String name;
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

}
