package org.makersoft.shards.mapper;

import java.util.List;

import org.makersoft.shards.domain.User;

/**
 * 
 */
public interface UserMapper {

//	@Select("SELECT * FROM maker_test_user WHERE id = #{id}")
	User getById(Long id);
	
//	@Select("SELECT * FROM maker_test_user")
	List<User> findAll();
	
//	@Insert("INSERT INTO maker_test_user (id,username,password) VALUES (#{id}, #{username},#{password})")
	int insertUser(User user);
	
//	@Delete("DELETE FROM maker_test_user WHERE id=#{id}")
	int deleteById(Long id);
	
	int udpateUser(User user);
	
	int getAllCount();
	
}
