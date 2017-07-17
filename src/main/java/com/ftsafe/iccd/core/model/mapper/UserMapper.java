package com.ftsafe.iccd.core.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ftsafe.iccd.core.model.User;

public class UserMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {

		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("userid"));
		user.setEmail(rs.getString("email"));
		user.setCreateTime(rs.getTimestamp("createtime"));
		user.setLevel(rs.getInt("level"));
		return user;
	}

}
