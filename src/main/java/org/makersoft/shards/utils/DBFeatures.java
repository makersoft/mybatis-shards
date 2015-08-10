package org.makersoft.shards.utils;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

public abstract class DBFeatures {
	private static final String POSTGRES_PART = "postgresql";
	private static final String MYSQL_PART = "mysql";
	private static final String ORACLE_PART = "oracle";
	// private static final String MSSQL_PART = "mssqlserver4";
	private static final String MSSQL_PART = "microsoft";
	private static final String HSQL_PART = "hsql";
	private static final String H2_PART = "h2";
	private static final String DB2_PART = "db2";
	private static final String SYBASE_SQLANY_PART = "sql anywhere";
	private static final String SQLITE_PART = "sqlite";

	public enum SQLDBType {
		unknown, postgres, mysql, oracle, mssql, hsql, h2, db2, sybase_sqlanywhere, sqlite;
	}

	private DBFeatures() {
	}

	public static SQLDBType discoverDBType(DataSource dataSource) {
		SQLDBType dbType = SQLDBType.unknown;
		try {
			DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
			String dbName = metaData.getDatabaseProductName().toLowerCase();
			if (dbName.indexOf(POSTGRES_PART) != -1) {
				return SQLDBType.postgres;
			} else if (dbName.indexOf(MYSQL_PART) != -1) {
				return SQLDBType.mysql;
			} else if (dbName.indexOf(ORACLE_PART) != -1) {
				return SQLDBType.oracle;
			} else if (dbName.indexOf(MSSQL_PART) != -1) {
				return SQLDBType.mssql;
			} else if (dbName.indexOf(HSQL_PART) != -1) {
				return SQLDBType.hsql;
			} else if (dbName.indexOf(H2_PART) != -1) {
				return SQLDBType.h2;
			} else if (dbName.indexOf(DB2_PART) != -1) {
				return SQLDBType.db2;
			} else if (dbName.indexOf(SYBASE_SQLANY_PART) != -1) {
				return SQLDBType.sybase_sqlanywhere;
			} else if (dbName.indexOf(SQLITE_PART) != -1) {
				return SQLDBType.sqlite;
			} else {
				return SQLDBType.unknown;
			}
		} catch (SQLException sqle) {
			// we can't do much here
		}
		return dbType;
	}
}
