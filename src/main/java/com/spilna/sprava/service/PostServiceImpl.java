package com.spilna.sprava.service;

import java.util.List;

import com.spilna.sprava.model.Post;
import com.spilna.sprava.businesslogic.object.PostRO;
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
	private PostDAO postDAO;

	@Override
	@Transactional
	/* Add message */
	public void addMessage( String token,Post message, String userId) {
		postDAO.saveMessage(token, message, userId);

	}
	
	public void saveMessage (String idP, String idU,String message){
		postDAO.saveMessage(idP, idU, message);
	}

	@Override
	@Transactional
	public List<PostRO> getMessage(String idUser) {
		return postDAO.getMessage(idUser);
	}
	
	public Integer getCountPost(String idUser){
		return postDAO.getCountPost(idUser);
	}

    @Override
    public void updatePost(Post post){
        postDAO.updatePost(post);
    }

    @Override
    public Post getPostByID(long id){
       return postDAO.getPostByID(id);
    }

	@Override
    public List<Post> getAllPostInf(){
        return postDAO.getAllPostInf();
    }

	@Override
	public void saveOrUpdatePost (List<com.restfb.types.Post> post, String idUser){
		postDAO.saveOrUpdatePost(post, idUser);
	}
}
