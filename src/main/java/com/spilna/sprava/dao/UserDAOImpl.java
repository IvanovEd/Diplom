package com.spilna.sprava.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import com.restfb.Parameter;
import com.spilna.sprava.Utils;
import org.codehaus.jackson.JsonNode;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateQueryException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restfb.DefaultFacebookClient;
import com.restfb.exception.FacebookException;
import com.restfb.types.User;
import com.spilna.sprava.model.UserIn;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author Ivanov Eduard
 * @version 1.0
 */
@Repository
@Transactional 
public class UserDAOImpl implements UserDAO {
	@Autowired  
    private SessionFactory sessionFactory;
    @Autowired
    private Utils utils;
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * Implementation of a method for saving the user and his data in the
	 * database via RestFB is a simple and flexible Facebook Graph API and Old
	 * REST API client written in Java.
	 * */
	@Override
	public void saveUser(String token) {
		DefaultFacebookClient fbClient = new DefaultFacebookClient(token);

		try {
			User me = fbClient.fetchObject("me", User.class, Parameter.with("fields","name,locale,location,about"));

			String id = me.getId().toString();
			String name = me.getName();
//			/** SQL query records in the table message, login and post */
//			Query q = openSession().createSQLQuery(
//							"INSERT INTO user(id_user,name,token) " + "VALUES"
//							+ " ('"+id+"','"+name+ "','"+token+"')"
//							+ " ON DUPLICATE KEY UPDATE token='" + token+ "'");

            String cityName = me.getLocation().getName();
            String region = utils.searchRegionByCity(cityName, token);


//            Query q = openSession().createQuery("insert into com.spilna.sprava.model.UserIn (id_user,name,token,city,region) values (:id,:name,:token,:city,:region)");
//            q.setParameter("id", id);
//            q.setParameter("name", name);
//            q.setParameter("token", token);
//            q.setParameter("city", cityName);
//            q.setParameter("region", region);
            UserIn userIn = new UserIn();
            userIn.setIdU(id);
            userIn.setCity(cityName);
            userIn.setRegion(region);
            userIn.setName(name);
            userIn.setToken(token);

			openSession().saveOrUpdate(userIn);
		} catch (FacebookException e) {
			e.printStackTrace();
		} catch (HibernateQueryException e) {
			e.printStackTrace();
		}
	}
	/**
	 * implementation of a method to receive the user
	 * 
	 * @return userList
	 */
	/** Suppression of compiler warnings */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserIn> getUser(String id) {

		/** Getting a list of users  */
		Criteria crit = openSession().createCriteria(UserIn.class);
		crit.add(Restrictions.like("idUser", id));
			List<UserIn>	userList=crit.addOrder(Order.desc("id")).list();
		return userList;
	}	 
}