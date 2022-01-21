package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Comment;
import beans.Customer;

public class CommentDAO {
	private String file;

	public CommentDAO(String file) {
		super();
		this.file = file;
	}
	
	public void saveAll(ArrayList<Comment> comments) throws IOException {
		 Gson gson = new Gson();
	     FileWriter fw = new FileWriter(this.file);
	     gson.toJson(comments, fw);
	     fw.flush();
	     fw.close();
	}
	
	public ArrayList<Comment> getAll(){
		 Gson gson = new Gson();
	     Type token = new TypeToken<ArrayList<Comment>>(){}.getType();
       BufferedReader br = null;
       try {
           br = new BufferedReader(new FileReader(this.file));
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
       
       return gson.fromJson(br, token);
	}

	public Comment create(Comment comment) throws IOException {
		ArrayList<Comment> comments = getAll();
		if(comments == null) {
			comments = new ArrayList<Comment>();
		}
		comments.add(comment);
		saveAll(comments);
		return comment;
	}
	
}
