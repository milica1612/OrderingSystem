package service;

import java.io.IOException;
import java.util.ArrayList;

import beans.Comment;
import beans.Restaurant;
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

	public ArrayList<Comment> getAllApprovedForRestaurant(String params) {
		ArrayList<Comment> all = commentDAO.getAll();
		ArrayList<Comment> result = new ArrayList<Comment>();
		for (Comment comment : all) {
			if(comment.getRestaurant().getName().equals(params) && comment.isApproved()) {
				result.add(comment);
			}
		}
		return result;
	}

	public Double calculateRating(Restaurant restaurant) {
		ArrayList<Comment> all = commentDAO.getAll();
		Double total = (double) 0;
		int count = 0;
		for (Comment comment : all) {
			if(comment.getRestaurant().getName().equals(restaurant.getName())) {
				total += comment.getRating();
				count++;
			}
		}
		if(count == 0) {
			return (double) 5;
		}
		return Math.round(total/count*100.0)/100.0;
	}
	
	

}
