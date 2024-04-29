package org.comit.spring.service;

import java.util.Collections;
import java.util.List;

import org.comit.spring.bean.Comment;
import org.comit.spring.dao.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
	
	@Autowired
	CommentDao commentDao;
	
	public Page<Comment> findPaginated(int idBook, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        
        List<Comment> comments = commentDao.listComments(idBook);

        if (comments.size() < startItem) {
        	comments = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, comments.size());
            comments = comments.subList(startItem, toIndex);
        }

        Page<Comment> commentPage = new PageImpl<Comment>(comments, PageRequest.of(currentPage, pageSize), comments.size());

        return commentPage;
    }
	
	public void createComment(Comment comment){
		this.commentDao.createComment(comment);
	}

}
