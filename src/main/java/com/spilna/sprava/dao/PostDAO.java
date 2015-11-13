package com.spilna.sprava.dao;

import java.util.List;

import com.restfb.types.Post;
import com.spilna.sprava.model.PostInf;
import com.spilna.sprava.model.PostRO;

/**
 * Interface MessageDAO
 * @author Ivanov Eduard
 * @version 1.0
 */

public interface PostDAO {
	
	/** Save and publish messages to facebook*/
	public void saveMessage (String token, PostInf message );
	/** Saving the post in the database*/
	public void saveMessage (String idP, String idU,String message); 
	/**Getting the user record from the database*/ 
	public List<PostRO> getMessage(String idUser);
	/**Obtaining records the number of records the user*/
	public Integer getCountPost(String idUser);
    public void updatePost(PostInf postInf);
    public PostInf getPostByID(long id);
    public List<PostInf> getAllPostInf();
	public void saveOrUpdatePost (Post post, String idUser);
    
}
