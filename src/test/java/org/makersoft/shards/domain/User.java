package org.makersoft.shards.domain;


/**
 * User domain for test.
 *  extends AbstractShardEntity 
 */
public class User{

	private static final long serialVersionUID = -3387466760287081837L;
	
	public static final int SEX_MALE = 0;
	public static final int SEX_FEMALE = 1;

	private Long id;

	private String username;

	private String password;

	private int sex;

	public User() {
	}

	public User(Long id, String username, String password, int sex) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.sex = sex;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

}
