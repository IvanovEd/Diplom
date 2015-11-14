package com.spilna.sprava.service;

import java.util.List;

import com.spilna.sprava.model.Post;
import com.spilna.sprava.model.PostRO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spilna.sprava.dao.PostDAO;

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
	public void addMessage( String token,Post message, String userId) {
		messageDAO.saveMessage(token, message, userId);

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

    @Override
    public void updatePost(Post post){
        messageDAO.updatePost(post);
    }

    @Override
    public Post getPostByID(long id){
       return messageDAO.getPostByID(id);
    }

	@Override
    public List<Post> getAllPostInf(){
        return messageDAO.getAllPostInf();
    }

	@Override
	public void saveOrUpdatePost (List<com.restfb.types.Post> post, String idUser){
		messageDAO.saveOrUpdatePost(post, idUser);
	}
}
