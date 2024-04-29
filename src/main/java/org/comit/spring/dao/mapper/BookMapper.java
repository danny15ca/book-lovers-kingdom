package org.comit.spring.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.comit.spring.bean.Book;
import org.springframework.jdbc.core.RowMapper;

public class BookMapper implements RowMapper<Book> {

	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {

		Book book = new Book();
		
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
		
		return book;
	}

}
