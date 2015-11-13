package com.spilna.sprava.model;

import com.spilna.sprava.businesslogic.objects.Interest;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Ivanov on 27.09.15.
 */
public class PostRO {

    private long id;

    private String idPost;

    private String idUser;

    private String post;

    private String interest;




    // implementation of methods getters and setters
    public PostRO() {};
    public PostRO(PostInf postInf) throws UnsupportedEncodingException {
        this.id = postInf.getId();
        this.idPost = postInf.getIdPost();
        this.idUser = postInf.getIdUser();
        this.post = URLDecoder.decode(postInf.getPost(), "UTF8");
        if (postInf.getInterestOfPost() != null) {
            this.interest = Interest.parse(Integer.valueOf(postInf.getInterestOfPost().getInterest())).toString();
        }
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
