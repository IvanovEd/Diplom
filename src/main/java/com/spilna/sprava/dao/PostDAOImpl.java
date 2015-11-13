package com.spilna.sprava.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.restfb.types.Post;
import com.spilna.sprava.Utils;
import com.spilna.sprava.businesslogic.objects.Interest;
import com.spilna.sprava.model.InterestOfPost;
import com.spilna.sprava.model.PostRO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateQueryException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.types.FacebookType;
import com.restfb.types.User;
import com.spilna.sprava.model.PostInf;
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
    public void saveMessage(String token, PostInf message) {

        DefaultFacebookClient fbClient = new DefaultFacebookClient(token);
        try {
            User me = fbClient.fetchObject("me", User.class);
            //Id User
            String id = me.getId().toString();
            //Message
            String mes = message.getPost();
            System.out.println("user id=" + id + " post=" + mes);

            FacebookType publishMessageResponse = fbClient.publish("me/feed",
                    FacebookType.class, Parameter.with("message", mes));

            String idP = publishMessageResponse.getId().substring(16);
            /** SQL query records in the table message, login and post */
            Query q = openSession().createSQLQuery(
                    "INSERT INTO post(id_post,id_user,message) " + "VALUES ('"
                            + idP + "','" + id + "', '" + mes + "')");

            q.executeUpdate();
        } catch (FacebookException e) {
            e.printStackTrace();
        } catch (HibernateQueryException e) {
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
        Criteria crit = session.createCriteria(PostInf.class);
        crit.add(Restrictions.like("idUser", idU));
        List<PostInf> mesList = crit.addOrder(Order.asc("id")).list();
        List<PostRO> postROs = new ArrayList<PostRO>();

        for (PostInf postInf : mesList) {

            try {
                postROs.add(new PostRO(postInf));
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
    public void updatePost(PostInf postInf) {
        try {
            Session session = openSession();
            session.update(postInf);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public PostInf getPostByID(long id) {
        PostInf postInf = null;
        try {
            Session session = openSession();
            postInf = (PostInf) session.get(PostInf.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return postInf;
    }

    @Override
    public List<PostInf> getAllPostInf(){
        List<PostInf> list = null;
        try {
            list = openSession().createCriteria(PostInf.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    @Transactional
    public void saveOrUpdatePost (Post post, String idUser) {
        try {
            Session session = openSession();
            PostInf postInf = new PostInf();
            postInf.setIdPost(post.getId());
            if (!StringUtils.isEmpty(post.getMessage())) {
                postInf.setPost(URLEncoder.encode(post.getMessage(), "UTF8"));
                List<PostInf> postInfList = getAllPostInf();
                if (!CollectionUtils.isEmpty(postInfList)) {
                    List<PostRO> postROList = new ArrayList<>();
                    for (PostInf postInfObj : postInfList) {
                        postROList.add(new PostRO(postInfObj));
                    }

                    InterestOfPost interestOfPost = new InterestOfPost();
                    interestOfPost.setInterest(String.valueOf(utils.getInterestByExistsInterestInPost(post.getMessage(), postROList).getValue()));
                    interestOfPost.setPostInf(postInf);
                    postInf.setInterestOfPost(interestOfPost);
                }
            }
            if (postInf.getInterestOfPost() == null) {
                InterestOfPost interestOfPost = new InterestOfPost();
                interestOfPost.setInterest(String.valueOf(Interest.OTHER.getValue()));
                interestOfPost.setPostInf(postInf);
                postInf.setInterestOfPost(interestOfPost);
            }
            postInf.setIdUser(idUser);
            InterestOfPost interestOfPost = postInf.getInterestOfPost();
            postInf.setInterestOfPost(null);
            session.save(postInf);
            postInf.setInterestOfPost(interestOfPost);
            session.save(postInf);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
