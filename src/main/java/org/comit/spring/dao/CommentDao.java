package org.comit.spring.dao;

import java.util.List;

import org.comit.spring.bean.Comment;
import org.comit.spring.dao.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Comment> listComments(int idBook) {

        String sql = "SELECT * FROM COMMENT C INNER JOIN BOOK B ON B.ID_BOOK = C.BOOK_ID" +
                       " INNER JOIN USER U ON U.ID_USER = C.USER_ID WHERE B.ID_BOOK = ?";

        return this.jdbcTemplate.query(sql, new CommentMapper(), idBook);
    }
	
	public void createComment(Comment comment) {
		String sql = "INSERT INTO COMMENT (COMMENT_MSG, COMMENT_DATE, BOOK_ID, USER_ID) " 
				+ "VALUES(?, ?, ?, ?)";

		this.jdbcTemplate.update(sql,
									comment.getCommentMsg(),
									comment.getCommentDate(),
									comment.getBook().getIdBook(),
									comment.getUser().getIdUser());
	}

}
