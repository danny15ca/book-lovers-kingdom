package org.comit.spring.service;

import java.util.Collections;
import java.util.List;

import org.comit.spring.bean.Book;
import org.comit.spring.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {
	
	@Autowired
	BookDao bookDao;
	
	public List<Book> listBooks() {
		
		List<Book> books = this.bookDao.listBooks();
		
		books.forEach(System.out::println);
		/*
		 * Apply some business logic to the users list
		 */
		
		return books;
	}
	
	public Page<Book> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        
        List<Book> books = bookDao.listBooks();

        if (books.size() < startItem) {
        	books = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, books.size());
            books = books.subList(startItem, toIndex);
        }

        Page<Book> bookPage = new PageImpl<Book>(books, PageRequest.of(currentPage, pageSize), books.size());

        return bookPage;
    }
	
	public Page<Book> findPaginatedFiltered(String search, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        
        List<Book> books = bookDao.findBook(search);

        if (books.size() < startItem) {
        	books = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, books.size());
            books = books.subList(startItem, toIndex);
        }

        Page<Book> bookPage = new PageImpl<Book>(books, PageRequest.of(currentPage, pageSize), books.size());

        return bookPage;
    }
	
	
	public void addNewBook(Book book){
		
		this.validateBook(book);
		
		this.bookDao.addNewBook(book);
	}
	
	public Book findBook(int idBook){
		
		return this.bookDao.findBook(idBook);
	}
	
	public void updateBook(Book book){
		
		this.bookDao.updateBook(book);
	}
	
	public void deleteBook(int idBook){
		this.bookDao.deleteBook(idBook);
	}
	
	private void validateBook(Book book) {
		
		if (book.getCoverImgPath().isEmpty() ||
				book.geteBookPath().isEmpty()) {
			
			throw new RuntimeException("Invalid Book Data: " + book.toString());
		}
			
	}

}
