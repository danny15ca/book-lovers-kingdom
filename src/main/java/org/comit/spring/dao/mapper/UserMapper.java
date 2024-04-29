package org.comit.spring.dao.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.comit.spring.bean.Address;
import org.comit.spring.bean.User;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {

		User user = new User();
		
		user.setIdUser(rs.getInt("ID_USER"));
		user.setUsername(rs.getString("USERNAME"));
		user.setPassword(rs.getString("PASSWORD"));
		user.setEmail(rs.getString("EMAIL"));
		user.setFirstName(rs.getString("FIRST_NAME"));
		user.setLastName(rs.getString("LAST_NAME"));
		user.setDob(rs.getDate("DOB"));
		user.setPhoneNumber(rs.getString("PHONE_NUMBER"));
		user.setStatus(rs.getString("STATUS"));
		user.setCreatedOn(rs.getDate("CREATED_ON"));
		user.setRole(rs.getString("ROLE"));
		
		Address address = new Address();
		if (hasColumn(rs, "ID_ADDRESS")) {
			address.setIdAddress(rs.getInt("ID_ADDRESS"));
			address.setAddress1(rs.getString("ADDRESS_1"));
			address.setAddress2(rs.getString("ADDRESS_2"));
			address.setCity(rs.getString("CITY"));
			address.setProvince(rs.getString("PROVINCE"));
			address.setPostalCode(rs.getString("POSTAL_CODE"));
			address.setCountry(rs.getString("COUNTRY"));
			address.setPhoneNumber(rs.getString("PHONE_NUMBER"));
			address.setPhoneType(rs.getString("PHONE_TYPE"));
			address.setIdUser(rs.getInt("USER_ID"));
		}
		user.setAddress(address);
		
		return user;
	}
	
	private boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		
		int numColumns = rsmd.getColumnCount();
		
		for (int i = 1; i <= numColumns; i++) {
			if (rsmd.getColumnName(i).equals(columnName)) {
				return true;
			}
		}
		
		return false;
	}

}
