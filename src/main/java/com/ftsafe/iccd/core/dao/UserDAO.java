package com.ftsafe.iccd.core.dao;

import java.util.List;

import javax.sql.DataSource;

import com.ftsafe.iccd.core.model.User;

public interface UserDAO {

	public void setDataSource(DataSource ds);
	
	public int create(String name, String password, String userId, String email, Integer level);
	
	public User getUser(Integer id);
	
	public List<User> listUsers();
	
	public int delete(Integer id);
	
	public int updateLevel(Integer id, Integer Level);
	
	public int updatePassword(Integer id, String password);
	
	public int updateName(Integer id, String name);
	
	public int updateEmail(Integer id, String email);
}
