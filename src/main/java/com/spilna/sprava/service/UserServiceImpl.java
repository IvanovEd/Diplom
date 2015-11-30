package com.spilna.sprava.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spilna.sprava.dao.UserDAO;

import com.spilna.sprava.model.User;

/**
 * @author Ivanov Eduard
 * @version 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional
	/* Add user */
	public void addUser(String token) {
		userDAO.saveUser(token);
	}

	@Override
	@Transactional
	/* Get user */
	public List<User> getUser(String id) {
		return userDAO.getUser(id);

	}

}
