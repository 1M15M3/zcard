package com.ftsafe.iccd.core.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.ftsafe.iccd.core.dao.UserDAO;
import com.ftsafe.iccd.core.model.User;
import com.ftsafe.iccd.core.model.mapper.UserMapper;

public class UserDAOImpl implements UserDAO{

	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(ds);
	}

	@Override
	public int create(String name, String password, String userId, String email, Integer level) {
		String SQL = "insert into USER (name, password, userid, email, level) values (?, ?, ?, ?, ?)";
		return jdbcTemplateObject.update(SQL, name, password, userId, email, level);
	}

	@Override
	public User getUser(Integer id) {
		String SQL = "select * from USER where id = ?";
		User user = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new UserMapper());
		return user;
	}

	@Override
	public List<User> listUsers() {
		String SQL = "select * from USER";
		List<User> user = jdbcTemplateObject.query(SQL, new UserMapper());
		return user;
	}

	@Override
	public int delete(Integer id) {
		String SQL = "delete fromr USER where id = ?";
		return jdbcTemplateObject.update(SQL, id);
	}

	@Override
	public int updateLevel(Integer id, Integer Level) {
		String SQL = "update USER set level = ? where id = ?";
		return jdbcTemplateObject.update(SQL, Level, id);
	}

	@Override
	public int updatePassword(Integer id, String password) {
		String SQL = "update USER set password = ? where id = ?";
		return jdbcTemplateObject.update(SQL, password, id);		
	}

	@Override
	public int updateName(Integer id, String name) {
		String SQL = "update USER set name = ? where id = ?";
		return jdbcTemplateObject.update(SQL, name, id);		
	}

	@Override
	public int updateEmail(Integer id, String email) {
		String SQL = "update USER set email = ? where id = ?";
		return jdbcTemplateObject.update(SQL, email, id);		
	}

}
