package org.comit.spring.dao.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.comit.spring.bean.Address;
import org.comit.spring.bean.Book;
import org.comit.spring.bean.Comment;
import org.comit.spring.bean.User;
import org.springframework.jdbc.core.RowMapper;

public class CommentMapper implements RowMapper<Comment> {

	@Override
	public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {

		Comment comment = new Comment();
		
		comment.setIdComment(rs.getInt("ID_COMMENT"));
		comment.setCommentMsg(rs.getString("COMMENT_MSG"));
		comment.setCommentDate(rs.getDate("COMMENT_DATE"));
		
		Book book = new Book();
		if (hasColumn(rs, "ID_BOOK")) {
			book.setIdBook(rs.getInt("ID_BOOK"));
			book.setTitle(rs.getString("TITLE"));
			book.setCoverImgPath(rs.getString("COVER_IMG_PATH"));
			book.setAuthorFirstName(rs.getString("AUTHOR_FIRST_NAME"));
			book.setAuthorLastName(rs.getString("AUTHOR_LAST_NAME"));
			book.setGenre(rs.getString("GENRE"));
			book.setRating(rs.getBigDecimal("RATING"));
			book.setPublisher(rs.getString("PUBLISHER"));
			book.setPublishingDate(rs.getDate("PUBLISHING_DATE"));
			book.setNumberOfPages(rs.getInt("NUMBER_OF_PAGES"));
			book.setLanguage(rs.getString("LANGUAGE"));
			book.setIsbn(rs.getString("ISBN"));
			book.setIsForSale(rs.getBoolean("IS_FOR_SALE"));
			book.setIsForRent(rs.getBoolean("IS_FOR_RENT"));
			book.setSalesPrice(rs.getBigDecimal("SALES_PRICE"));
			book.setRentPrice(rs.getBigDecimal("RENT_PRICE"));
			book.seteBookPath(rs.getString("EBOOK_PATH"));
			book.setSalesQuantity(rs.getInt("SALES_QUANTITY"));
		}
		comment.setBook(book);
		
		User user = new User();
		if (hasColumn(rs, "ID_USER")) {
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
		}
		comment.setUser(user);
		
		return comment;
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
