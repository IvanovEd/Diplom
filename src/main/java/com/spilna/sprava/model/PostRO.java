package com.spilna.sprava.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Ivanov on 27.09.15.
 */
public class PostRO {
    private String id;

    private String idPost;

    private String idUser;

    private String post;

    // implementation of methods getters and setters
    public PostRO() {};
    public PostRO(PostInf postInf) throws UnsupportedEncodingException {
        this.id = postInf.getId();
        this.idPost = postInf.getIdPost();
        this.idUser = postInf.getIdUser();
        this.post = URLDecoder.decode(postInf.getPost(), "UTF8");
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String id) {
        this.idPost = id;
    }
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }



}
