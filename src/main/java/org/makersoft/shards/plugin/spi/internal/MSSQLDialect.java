package org.makersoft.shards.plugin.spi.internal;

import org.makersoft.shards.plugin.spi.Dialect;

/**
 * @Description: MicroSoft SQLServer Dialect
 * @Name MSSQLDialect
 * @Company ZSword (C) Copyright
 * @Author JemiZhuu(周士淳)
 * @Date 2015年8月10日 上午9:14:48
 * @Version 1.0
 */
public class MSSQLDialect implements Dialect {

	@Override
	public boolean supportLimit() {
		return true;
	}

	@Override
	public boolean supportOffsetLimit() {
		return true;
	}

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (offset > 0) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}

		pagingSelect.append(sql);

		if (offset > 0) {
			pagingSelect.append(" ) row_ ) where rownum_ <= ").append(offset + limit)
					.append(" and rownum_ > ").append(offset);
		} else {
			pagingSelect.append(" ) where rownum <= " + limit);
		}

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}

}
