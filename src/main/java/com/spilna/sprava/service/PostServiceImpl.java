package com.spilna.sprava.service;

import java.util.List;

import com.spilna.sprava.model.PostRO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spilna.sprava.dao.PostDAO;
import com.spilna.sprava.model.PostInf;

/**
 * 
 * @author Ivanov Eduard
 * @version 1.0
 */
@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDAO messageDAO;

	@Override
	@Transactional
	/* Add message */
	public void addMessage( String token,PostInf message) {
		messageDAO.saveMessage(token, message);

	}
	
	public void saveMessage (String idP, String idU,String message){
		messageDAO.saveMessage(idP, idU, message);
	}

	@Override
	@Transactional
	public List<PostRO> getMessage(String idUser) {
		return messageDAO.getMessage(idUser);
	}
	
	public Integer getCountPost(String idUser){
		return messageDAO.getCountPost(idUser);
	}

}
