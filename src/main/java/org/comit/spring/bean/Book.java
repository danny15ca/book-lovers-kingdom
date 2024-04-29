package org.comit.spring.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Book {
	
	int idBook;
	String title;
	String coverImgPath;
	String authorFirstName;
	String authorLastName;
	String genre;
	BigDecimal rating;
	String publisher;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date publishingDate;
	
	int numberOfPages;
	String language;
	String isbn;
	boolean isForSale;
	boolean isForRent;
	BigDecimal salesPrice;
	BigDecimal rentPrice;
	String eBookPath;
	int salesQuantity;
	
	public int getIdBook() {
		return idBook;
	}
	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCoverImgPath() {
		return coverImgPath;
	}
	public void setCoverImgPath(String coverImgPath) {
		this.coverImgPath = coverImgPath;
	}
	
	public String getAuthorFirstName() {
		return authorFirstName;
	}
	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}
	
	public String getAuthorLastName() {
		return authorLastName;
	}
	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}
	
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public BigDecimal getRating() {
		return rating;
	}
	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}
	
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public Date getPublishingDate() {
		return publishingDate;
	}
	public void setPublishingDate(Date publishingDate) {
		this.publishingDate = publishingDate;
	}
	
	public int getNumberOfPages() {
		return numberOfPages;
	}
	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public boolean getIsForSale() {
		return isForSale;
	}
	public void setIsForSale(boolean isForSale) {
		this.isForSale = isForSale;
	}
	
	public boolean getIsForRent() {
		return isForRent;
	}
	public void setIsForRent(boolean isForRent) {
		this.isForRent = isForRent;
	}
	
	public BigDecimal getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}
	
	public BigDecimal getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(BigDecimal rentPrice) {
		this.rentPrice = rentPrice;
	}
	
	public String geteBookPath() {
		return eBookPath;
	}
	public void seteBookPath(String eBookPath) {
		this.eBookPath = eBookPath;
	}
	
	public int getSalesQuantity() {
		return salesQuantity;
	}
	public void setSalesQuantity(int salesQuantity) {
		this.salesQuantity = salesQuantity;
	}
	
	@Override
	public String toString() {
		return "Book [idBook=" + idBook + ", title=" + title + ", coverImgPath=" + coverImgPath + ", authorFirstName="
				+ authorFirstName + ", authorLastName=" + authorLastName + ", genre=" + genre + ", rating=" + rating
				+ ", publisher=" + publisher + ", publishingDate=" + publishingDate + ", numberOfPages=" + numberOfPages
				+ ", language=" + language + ", isbn=" + isbn + ", isForSale=" + isForSale + ", isForRent=" + isForRent
				+ ", salesPrice=" + salesPrice + ", rentPrice=" + rentPrice + ", eBookPath=" + eBookPath
				+ ", salesQuantity=" + salesQuantity + "]";
	} 

}
