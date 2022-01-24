package service;

import java.io.IOException;
import java.util.ArrayList;

import beans.Comment;
import beans.CommentStatus;
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
			if(comment.getRestaurant().getName().equals(params) && comment.getStatus() == CommentStatus.APPROVED) {
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

	public ArrayList<Comment> getAllForRestaurant(String params) {
		ArrayList<Comment> all = commentDAO.getAll();
		return all;
	}

	public Comment approve(Comment comment) throws IOException {
		ArrayList<Comment> all = commentDAO.getAll();
		for (Comment comment2 : all) {
			if(comment2.getCode().equals(comment.getCode())) {
				comment2.setStatus(CommentStatus.APPROVED);
				commentDAO.saveAll(all);
				return comment2;
			}
		}
		return null;
		
	}

	public Comment decline(Comment comment) throws IOException {
		ArrayList<Comment> all = commentDAO.getAll();
		for (Comment comment2 : all) {
			if(comment2.getCode().equals(comment.getCode())) {
				comment2.setStatus(CommentStatus.DECLINED);
				commentDAO.saveAll(all);
				return comment2;
			}
		}
		return null;
	}
	

}
