package org.comit.spring.dao;

import java.math.BigDecimal;
import java.util.List;

import org.comit.spring.bean.Book;
import org.comit.spring.dao.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void addNewBook(Book book) {
		String sql = "INSERT INTO BOOK (TITLE, COVER_IMG_PATH, AUTHOR_FIRST_NAME, AUTHOR_LAST_NAME, GENRE, "
						+ "RATING, PUBLISHER, PUBLISHING_DATE, NUMBER_OF_PAGES, LANGUAGE, ISBN, IS_FOR_SALE, "
						+ "IS_FOR_RENT, SALES_PRICE, RENT_PRICE, EBOOK_PATH, SALES_QUANTITY) " 
						+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		this.jdbcTemplate.update(sql,
									book.getTitle(),
									book.getCoverImgPath(),
									book.getAuthorFirstName(),
									book.getAuthorLastName(),
									book.getGenre(),
									BigDecimal.valueOf(0.0),
									book.getPublisher(),
									book.getPublishingDate(),
									book.getNumberOfPages(),
									book.getLanguage(),
									book.getIsbn(),
									book.getIsForSale(),
									book.getIsForRent(),
									book.getSalesPrice(),
									book.getRentPrice(),
									book.geteBookPath(),
									book.getSalesQuantity());
	}
	
	public List<Book> listBooks(){
		
		String sql = "SELECT * FROM BOOK";
		
		return this.jdbcTemplate.query(sql, new BookMapper());
	}
	
	public Book findBook(int idBook){
		
		String sql = "SELECT * FROM BOOK WHERE ID_BOOK = ?";
		
		return DataAccessUtils.singleResult(this.jdbcTemplate.query(sql, new BookMapper(), idBook));
	}
	
	public List<Book> findBook(String text) {
		String sql = "SELECT * FROM BOOK WHERE UPPER(TITLE) LIKE UPPER(?) OR UPPER(GENRE) LIKE UPPER(?) "
						+ "OR UPPER(AUTHOR_FIRST_NAME) LIKE UPPER(?) OR UPPER(AUTHOR_LAST_NAME) LIKE UPPER(?)";
		
		return this.jdbcTemplate.query(sql,
										new BookMapper(), 
										"%" + text + "%", 
										"%" + text + "%", 
										"%" + text + "%", 
										"%" + text + "%");
	}
	
	public void updateBook(Book book){
		
		String sql = "UPDATE BOOK SET TITLE = ?, COVER_IMG_PATH = ?, AUTHOR_FIRST_NAME = ?, AUTHOR_LAST_NAME = ? "
						+ ", GENRE = ?, PUBLISHER = ?, PUBLISHING_DATE = ?, NUMBER_OF_PAGES = ?, LANGUAGE = ? "
						+ ", ISBN = ?, IS_FOR_SALE = ?, IS_FOR_RENT = ?, SALES_PRICE = ?, RENT_PRICE = ? "
						+ ", SALES_QUANTITY = ? "
						+ "WHERE ID_BOOK = ?";
		
		this.jdbcTemplate.update(sql,
									book.getTitle(),
									book.getCoverImgPath(),
									book.getAuthorFirstName(),
									book.getAuthorLastName(),
									book.getGenre(),
									book.getPublisher(),
									book.getPublishingDate(),
									book.getNumberOfPages(),
									book.getLanguage(),
									book.getIsbn(),
									book.getIsForSale(),
									book.getIsForRent(),
									book.getSalesPrice(),
									book.getRentPrice(),
									book.getSalesQuantity(),
									book.getIdBook());
	}
	
	public void deleteBook(int idBook){
		String sql = "DELETE FROM BOOK WHERE ID_BOOK = ?";
		
		this.jdbcTemplate.update(sql, idBook);
	}

}
