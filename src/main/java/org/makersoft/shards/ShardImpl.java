package org.makersoft.shards;

import static org.apache.ibatis.reflection.ExceptionUtil.unwrapThrowable;
import static org.mybatis.spring.SqlSessionUtils.closeSqlSession;
import static org.mybatis.spring.SqlSessionUtils.isSqlSessionTransactional;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Set;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.dao.support.PersistenceExceptionTranslator;

/**
 * 
 */
public class ShardImpl implements Shard {
	
	private final SqlSessionFactory sqlSessionFactory;
	
	private final PersistenceExceptionTranslator exceptionTranslator;
	
	private final SqlSession sqlSessionProxy;
	
	private final Set<ShardId> shardIds;

	public final ExecutorType executorType = ExecutorType.SIMPLE;
	
	public ShardImpl(Set<ShardId> shardIds, SqlSessionFactory sqlSessionFactory){
		this.shardIds = shardIds;
		this.sqlSessionFactory = sqlSessionFactory;
		this.exceptionTranslator = new MyBatisExceptionTranslator(
	            sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(), true);
		this.sqlSessionProxy = (SqlSession) Proxy.newProxyInstance(
		        SqlSessionFactory.class.getClassLoader(),
		        new Class[] { SqlSession.class },
		        new SqlSessionInterceptor());
	}
	
	private Configuration getConfiguration(){
		return sqlSessionFactory.getConfiguration();
	}
	
	@Override
	public SqlSessionFactory getSqlSessionFactory(){
		return sqlSessionFactory;
	}

	@Override
	public SqlSession getSqlSession() {
		return sqlSessionProxy;
	}

	@Override
	public Set<ShardId> getShardIds() {
		return shardIds;
	}

	@Override
	public SqlSession establishSqlSession() {
//		if(sqlSession == null){
//			sqlSession = sqlSessionFactory.openSession();
//		}
		return sqlSessionProxy;
	}

	@Override
	public Collection<String> getMappedStatementNames() {
		return getConfiguration().getMappedStatementNames();
	}
	
	@Override
	public boolean hasMapper(Class<?> type) {
		return getConfiguration().hasMapper(type);
	}
	
	private class SqlSessionInterceptor implements InvocationHandler {
	    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	      final SqlSession sqlSession = SqlSessionUtils.getSqlSession(
	    		  ShardImpl.this.sqlSessionFactory,
	    		  ShardImpl.this.executorType ,
	    		  ShardImpl.this.exceptionTranslator);
	      try {
	        Object result = method.invoke(sqlSession, args);
	        if (!isSqlSessionTransactional(sqlSession, ShardImpl.this.sqlSessionFactory)) {
	          // force commit even on non-dirty sessions because some databases require
	          // a commit/rollback before calling close()
	          sqlSession.commit(true);
	        }
	        return result;
	      } catch (Throwable t) {
	        Throwable unwrapped = unwrapThrowable(t);
	        if (ShardImpl.this.exceptionTranslator != null && unwrapped instanceof PersistenceException) {
	          Throwable translated = ShardImpl.this.exceptionTranslator.translateExceptionIfPossible((PersistenceException) unwrapped);
	          if (translated != null) {
	            unwrapped = translated;
	          }
	        }
	        throw unwrapped;
	      } finally {
	        closeSqlSession(sqlSession, ShardImpl.this.sqlSessionFactory);
	      }
	    }
	  }

}
