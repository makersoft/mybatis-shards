package org.makersoft.shards.mapper.impl;

import java.util.List;

import org.makersoft.shards.domain.User;
import org.makersoft.shards.mapper.UserDao;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao{

	@Override
	public int insertUser(User user) {
		return super.getSqlSession().insert("insertUser", user);
	}
	
	@Override
	public User getById(Long id) {
		return super.getSqlSession().selectOne("getById", id);
	}

	@Override
	public List<User> findAll() {
		return super.getSqlSession().selectList("findAllUsers");
	}

	@Override
	public int udpateUser(User user) {
		return super.getSqlSession().update("udpateUser", user);
	}

	@Override
	public int deleteById(Long id) {
		return super.getSqlSession().delete("deleteById", id);
	}

	@Override
	public int getAllCount() {
		return super.getSqlSession().<Integer>selectOne("UserMapper.count");
	}

	@Override
	public int updateById(User user) {
		return super.getSqlSession().update("updateById", user);
	}

}
