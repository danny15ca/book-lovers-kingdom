package org.comit.spring.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.comit.spring.bean.User;
import org.comit.spring.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void signUpUser(User user) {
		String sql = "INSERT INTO USER (USERNAME, PASSWORD, EMAIL, FIRST_NAME, LAST_NAME, DOB, PHONE_NUMBER, STATUS, CREATED_ON, ROLE) " 
	               + "VALUES(?,?,?,?,?,?, ?, ?, ?, ?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		this.jdbcTemplate.update(conn -> {
			
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getFirstName());
			ps.setString(5, user.getLastName());
			ps.setDate(6, new Date(user.getDob().getTime()));
			ps.setString(7, user.getPhoneNumber());
			ps.setString(8, "A");
			ps.setDate(9, new Date(new java.util.Date().getTime()));
			ps.setString(10, "ROLE_USER");
			
			return ps;
		}, keyHolder);
		
		String sqlUserAddress = "INSERT INTO ADDRESS (ADDRESS_1, ADDRESS_2, CITY, PROVINCE, POSTAL_CODE, "
									+ "COUNTRY, PHONE_NUMBER, PHONE_TYPE, USER_ID) " 
	               					+ "VALUES(?,?, ?, ?, ?, ?, ?, ?, ?)";
		
		this.jdbcTemplate.update(sqlUserAddress,
									user.getAddress().getAddress1(),
									user.getAddress().getAddress2(),
									user.getAddress().getCity(),
									user.getAddress().getProvince(),
									user.getAddress().getPostalCode(),
									"CA",
									user.getPhoneNumber(),
									"Mobile",
									keyHolder.getKey().intValue());
	}
	
	public User findUser(String username) {
		
		String sql = "SELECT * FROM USER WHERE UPPER(USERNAME) = UPPER(?)";
		
		return DataAccessUtils.singleResult(this.jdbcTemplate.query(sql,
											new UserMapper(), 
											username));
	}
	
	public User findUser(User user){
		
		String sql = "SELECT * FROM USER WHERE ID_USER != ? AND UPPER(USERNAME) = UPPER(?)";
		
		return DataAccessUtils.singleResult(
										this.jdbcTemplate.query(sql,
																new UserMapper(),
																user.getIdUser(),
																user.getUsername()));
	}

}
