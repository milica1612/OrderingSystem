package service;

import java.io.IOException;

import beans.Comment;
import dao.CommentDAO;

public class CommentService {
	private CommentDAO commentDAO;

	public CommentService(CommentDAO commentDAO) {
		super();
		this.commentDAO = commentDAO;
	}

	public Comment create(Comment comment) throws IOException {
		return commentDAO.create(comment);
	}
	
	

}
