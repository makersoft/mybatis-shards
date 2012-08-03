package org.makersoft.shards.strategy.exit.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.makersoft.shards.strategy.exit.ExitOperationsCollector;

/**
 * 
 */
public class ExitOperationsSelectOneCollector implements ExitOperationsCollector {

	private final String statement;
	
	
	public ExitOperationsSelectOneCollector(String statement){
		this.statement = statement;
	}
	
	@Override
	public List<Object> apply(List<Object> result) {
		if(statement.endsWith("count")){
			return new AggregateExitOperation("sum").apply(result);
		}else if(statement.endsWith("sum")){
			return new AggregateExitOperation("sum").apply(result);
		}else if(statement.endsWith("min")){
			return new AggregateExitOperation("min").apply(result);
		}else if(statement.endsWith("max")){
			return new AggregateExitOperation("max").apply(result);
		}else if(statement.endsWith("avg")){
			return new AvgResultsExitOperation().apply(result);
		}
		
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSqlSessionFactory(SqlSessionFactory sessionFactory) {

	}

}
