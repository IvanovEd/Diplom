package com.spilna.sprava.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.spilna.sprava.businesslogic.utils.Utils;
import com.spilna.sprava.businesslogic.enums.Interest;
import com.spilna.sprava.model.InterestOfPost;
import com.spilna.sprava.model.Post;
import com.spilna.sprava.businesslogic.object.PostRO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restfb.exception.FacebookException;
import com.restfb.types.FacebookType;
import org.springframework.util.CollectionUtils;

/**
 * @author Ivanov Eduard
 * @version 1.0
 */
@Repository
@Transactional
public class PostDAOImpl implements PostDAO {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Utils utils;

    private String SQL_SAVE_POST = "INSERT INTO post(id_post,id_user,message) VALUES (?,?,?) ON DUPLICATE KEY UPDATE id_post=";

    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    /**Implementation of a method for storing messages and
     * publish hear in facebook via RestFB is a simple and
     * flexible Facebook Graph API and Old REST API client written in Java.
     * */
    public void saveMessage(String token, Post post, String userId) {

        try {
            com.restfb.types.Post postFB = new com.restfb.types.Post();
            FacebookType publishPostOnFB = utils.publishPostOnFB(token, post.getPost());
            postFB.setId(publishPostOnFB.getId());
            postFB.setMessage(post.getPost());
            savePost(postFB, userId);
        } catch (FacebookException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method of store the status the database.
     * Variable "message" takes pareametry database of Facebook (table "status")
     */
    @Override
    public void saveMessage(String idP, String idU, String message) {

//		 Query q= openSession().createSQLQuery("INSERT INTO post(id_post,id_user,message) "
//												+ "VALUES ('"+idP+"','"+idU+"','"+message+"')"
//												+ " ON DUPLICATE KEY UPDATE id_post='"+idP+"'");
//
//		 q.executeUpdate();

//        jdbcTemplate.execute("INSERT INTO post(id_post,id_user,message) "
//                + "VALUES ('"+idP+"','"+idU+"','"+message+"')"
//                + " ON DUPLICATE KEY UPDATE id_post='"+idP+"'");
        if (message != null) {
            try {
                message = URLEncoder.encode(message, "UTF8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            jdbcTemplate.update(SQL_SAVE_POST + "\'" + idP + "\'", new Object[]{idP, idU, message});

        }

    }

    /**
     * Implementation of a method to receive the list message
     *
     * @return mesList
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PostRO> getMessage(String idU) {
        Session session = openSession();
        Criteria crit = session.createCriteria(Post.class);
        crit.add(Restrictions.like("idUser", idU));
        List<Post> mesList = crit.addOrder(Order.asc("id")).list();
        List<PostRO> postROs = new ArrayList<PostRO>();

        for (Post post : mesList) {

            try {
                postROs.add(new PostRO(post));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return postROs;
    }

    /**
     * method of counting the number of records in the database requirements
     *
     * @return amount records
     */
    @Override
    public Integer getCountPost(String idU) {

        String g = openSession().createSQLQuery(
                "SELECT COUNT(id_post) FROM post WHERE id_user= '" + idU + "'").list().toString();
        return Integer.parseInt(g.substring(1, g.indexOf(']')));
    }

    @Override
    public void updatePost(Post post) {
        try {
            Session session = openSession();
            session.update(post);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Post getPostByID(long id) {
        Post post = null;
        try {
            Session session = openSession();
            post = (Post) session.get(Post.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return post;
    }

    @Override
    public List<Post> getAllPostInf() {
        List<Post> list = null;
        try {
            list = openSession().createCriteria(Post.class).addOrder(Order.asc("id")).list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    @Transactional
    public void saveOrUpdatePost(List<com.restfb.types.Post> postList, String idUser) {
        List<PostRO> postROList = getMessage(idUser);
        if (postROList.isEmpty()) {
            for (com.restfb.types.Post postObj : postList) {
                if (!StringUtils.isEmpty(postObj.getMessage())) {
                    savePost(postObj, idUser);
                }
            }
        } else {
            for (com.restfb.types.Post postObj : postList) {
                boolean contains = false;
                for (PostRO postRO : postROList) {
                    if (postRO.getIdPost().equals(postObj.getId()) || StringUtils.isEmpty(postObj.getMessage())) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    savePost(postObj, idUser);
                } else if (!StringUtils.isEmpty(postObj.getMessage())) {
                    Interest interest = utils.getInterestByExistsInterestInPost(postObj.getMessage(), postROList);
                    selfUpdateOfInterest(postObj, interest);
                }
            }
        }

    }

    private void savePost(com.restfb.types.Post post, String idUser) {
        try {
            Session session = openSession();
            Post postInf = new Post();
            postInf.setIdPost(post.getId());
            if (!StringUtils.isEmpty(post.getMessage())) {
                postInf.setPost(URLEncoder.encode(post.getMessage(), "UTF8"));
                List<Post> postList = getAllPostInf();
                if (!CollectionUtils.isEmpty(postList)) {
                    List<PostRO> postROList = new ArrayList<>();
                    for (Post postObj : postList) {
                        postROList.add(new PostRO(postObj));
                    }

                    InterestOfPost interestOfPost = new InterestOfPost();
                    interestOfPost.setInterest(String.valueOf(utils.getInterestByExistsInterestInPost(post.getMessage(), postROList).getValue()));
                    interestOfPost.setPost(postInf);
                    postInf.setInterestOfPost(interestOfPost);
                }
            }
            if (postInf.getInterestOfPost() == null) {
                InterestOfPost interestOfPost = new InterestOfPost();
                interestOfPost.setInterest(String.valueOf(Interest.OTHER.getValue()));
                interestOfPost.setPost(postInf);
                postInf.setInterestOfPost(interestOfPost);
            }
            if (idUser != null) {
                postInf.setIdUser(idUser);
            }

            InterestOfPost interestOfPost = postInf.getInterestOfPost();
            postInf.setInterestOfPost(null);
            session.save(postInf);
            postInf.setInterestOfPost(interestOfPost);
            session.save(postInf);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }


    private void selfUpdateOfInterest(com.restfb.types.Post postFromFB, Interest interest) {
        Session session = openSession();
        Criteria crit = session.createCriteria(Post.class);
        crit.add(Restrictions.eq("idPost", postFromFB.getId()));
        Post post = (Post) crit.list().get(0);
        post.getInterestOfPost().setInterest(String.valueOf(interest.getValue()));
        session.update(post);
    }
}
