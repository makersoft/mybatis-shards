package org.makersoft.shards.spring;

import java.io.Serializable;

import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * 
 */
public abstract class ShardedSqlSessionDaoSupport<T,PK extends Serializable> extends SqlSessionDaoSupport {
	
	protected final Class<T> clazz;
	
	protected final String selectOneStatement;
	
	public ShardedSqlSessionDaoSupport(){
		this.clazz = null;
		this.selectOneStatement = "select" + clazz.getName();
	}
	
	public T get(Class<T> clazz, final PK id){
		return super.getSqlSession().<T>selectOne(selectOneStatement, id);
	}
	
	
	public void save(T entity){
		super.getSqlSession().insert("insert" + clazz.getName(), entity);
	}
	
	public void update(T entity){
		
	}
	
	public void saveOrUpdate(T entity){
		
	}
	
	public void delete(T entity){
		
	}
	
	public T get(final PK id){
		return this.get(clazz, id);
	}
	
	public void delete(PK id){
//		this.delete((T)Class.forName(clazz.getName()).newInstance());
	}
	
}
