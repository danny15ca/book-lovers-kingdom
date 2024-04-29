package org.comit.spring.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Comment {
	
	int idComment;
	String commentMsg;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date commentDate;
	
	Book book;
	User user;
	
	public int getIdComment() {
		return idComment;
	}
	public void setIdComment(int idComment) {
		this.idComment = idComment;
	}
	public String getCommentMsg() {
		return commentMsg;
	}
	public void setCommentMsg(String commentMsg) {
		this.commentMsg = commentMsg;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "Comment [idComment=" + idComment + ", commentMsg=" + commentMsg + ", commentDate=" + commentDate
				+ ", book=" + book + ", user=" + user + "]";
	}

}
