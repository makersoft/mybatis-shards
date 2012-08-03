package org.makersoft.shards.transaction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractTransactionStatus;

/**
 * 
 */
public class MultiDataSourcesTransactionStatus extends
		AbstractTransactionStatus {
	
	private Map<DataSource, TransactionStatus> dataSourceTransactionStatusMap = new ConcurrentHashMap<DataSource, TransactionStatus>();
	
	@Override
	public boolean isNewTransaction() {
		return true;
	}
	
	/**
	 * 设置
	 * @param dataSource
	 * @param transactionStatus
	 * @return
	 */
	public Map<DataSource, TransactionStatus> put(DataSource dataSource, TransactionStatus transactionStatus){
		dataSourceTransactionStatusMap.put(dataSource, transactionStatus);
		return dataSourceTransactionStatusMap;
	}
	
	public TransactionStatus get(DataSource dataSource) {
		return dataSourceTransactionStatusMap.get(dataSource);
	}

}
