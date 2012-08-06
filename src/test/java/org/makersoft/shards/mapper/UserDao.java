package org.makersoft.shards.mapper;

import java.util.List;

import org.makersoft.shards.domain.User;

/**
 * User dao for test.
 */
public interface UserDao {

	int insertUser(User user);
	
	User getById(String id);
	
	List<User> findAll();
	
	List<User> findByGender(int gender);
	
	int deleteAll();
	
	int deleteById(String id);
	
	int udpateUser(User user);
	
	int updateById(User user);
	
	int getAllCount();
	
}
