package org.makersoft.shards.mapper;

import java.util.List;

import org.makersoft.shards.domain.User;

/**
 * User dao for test.
 */
public interface UserDao {

	int insertUser(User user);
	
	User getById(Long id);
	
	List<User> findAll();
	
	int deleteById(Long id);
	
	int udpateUser(User user);
	
	int updateById(User user);
	
	int getAllCount();
	
}
