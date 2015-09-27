package com.spilna.sprava.service;

import java.util.List;

import com.spilna.sprava.model.PostInf;
import com.spilna.sprava.model.PostRO;

/**
 * Interface MessageService
 * @author Ivanov Eduard
 * @version 1.0
 */
public interface PostService {
	
	/** Save and publish messages to facebook*/
	public void addMessage (String token, PostInf message );
	/** Saving the post in the database*/
	public void saveMessage (String idP, String idU,String message); 
	/**Getting the user record from the database*/ 
	public List<PostRO> getMessage(String idUser);
	/**Obtaining records the number of records the user*/
	public Integer getCountPost(String idUser);

}
